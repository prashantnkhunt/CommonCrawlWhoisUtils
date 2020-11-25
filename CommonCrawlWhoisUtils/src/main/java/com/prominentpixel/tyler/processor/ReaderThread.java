package com.prominentpixel.tyler.processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWhoisWrapper;
import com.prominentpixel.tyler.mapper.WrapperToDomain;
import com.prominentpixel.tyler.utils.PropertyUtils;

public class ReaderThread implements Runnable {
    private static final Logger logger = Logger.getLogger(ReaderThread.class);


    private LinkedBlockingQueue<ProcessorRecord> linkedBlockingQueue;
    private BufferedReader reader;
    private ObjectMapper mapper = new ObjectMapper();
    private RestHighLevelClient client = null;
    private Properties properties = null;
    private String indexName;

    public ReaderThread(LinkedBlockingQueue<ProcessorRecord> linkedBlockingQueue, String filePath) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        try {
            this.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR While initializing ReaderThread::", e);
        }
    }

    private void init(String filePath) throws Exception {

        reader = new BufferedReader(new FileReader(filePath));
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        //Configuring Properties
        properties = PropertyUtils.loadConfigProperties("config.properties");

        //Configuring Logs
        String log4jConfigFile = PropertyUtils.getLogFilePropertyPath();
        PropertyConfigurator.configure(log4jConfigFile);

        indexName = properties.getProperty("ES_INDEX");

        boolean isTerminatedGracefully = false;
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        logger.info("start time::" + new Date());

        try {

            //TODO:Check for Secure VS Simple
//            if (isSecureRESTClientInitialized()) {
            if (isRESTClientInitialized()) {

            } else {
                logger.error("ElasticSearch is not initialized properly.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("Exception Generated::", exception);
        }
    }

    @Override
    public void run() {
        try {

            WrapperToDomain wrapperToDomain = new WrapperToDomain();

            for (String line; (line = reader.readLine()) != null; ) {

            	CCRecordWhoisWrapper loadingObject = mapper.readValue(line, CCRecordWhoisWrapper.class);
                CCRecord record = wrapperToDomain.convertWrapperToDomainWhoIsEmails(loadingObject);

                //FindByDomainAndEmail
                //{"query":{"bool":{"must":[{"match":{"domain":"data"}},{"match":{"email":"data"}}],"must_not":[],"should":[]}},"from":0,"size":10,"sort":[],"aggs":{}}
//                GetRequest getRequest = new GetRequest(indexName,record.getDomain());
                
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("domain", record.getDomain()))
                        .must(QueryBuilders.matchQuery("email", record.getEmail()));
                
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.query(boolQueryBuilder);
               
                SearchRequest searchRequest = new SearchRequest(indexName);
                searchRequest.source(searchSourceBuilder);
//                System.out.println("SEARCH, "+searchSourceBuilder);//
                SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
                
//                GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

//                ProcessorRecord processorRecord = new ProcessorRecord();
                List<CCRecord> ccRecordList;

                long totalCount =searchResponse.getHits().getTotalHits().value;
                if (totalCount > 0) {
                	
                	for (SearchHit searchHit : searchResponse.getHits()) {
						CCRecord existingDbRec = mapper.readValue(searchHit.getSourceAsString(), CCRecord.class);
                		boolean updated = fillChanges(record, existingDbRec);

                		if(updated) {
                            ProcessorRecord processorRecord = new ProcessorRecord();
                            processorRecord.setId(searchHit.getId());
                            processorRecord.setCcRecord(existingDbRec);
                            processorRecord.setEof(false);
                            linkedBlockingQueue.put(processorRecord);
                		}

//                		domainMinimals.add(domainToDto.convertMapToDomainMinimal(searchHit.getSourceAsMap()));
                	}
					/*
					String sourceAsString = getResponse.getSourceAsString();
					ccRecordList = mapper.readValue(sourceAsString, new TypeReference<List<CCRecord>>(){});
					
					//If not present in email list then only insert.
					if(!ccRecordList.contains(record)){
					    processorRecord.setCcRecord(record);
					    processorRecord.setEof(false);
					    linkedBlockingQueue.put(processorRecord);
					}*/
                }
                else {
                    //Means Record is not found..
                    ProcessorRecord processorRecord = new ProcessorRecord();
                    processorRecord.setCcRecord(record);
                    processorRecord.setEof(false);
                    linkedBlockingQueue.put(processorRecord);
                }
            }
			/*
			ProcessorRecord eofRecord = new ProcessorRecord();
			eofRecord.setEof(true);
			linkedBlockingQueue.put(eofRecord);*/

        } catch (Exception e) {
            logger.error("ERROR While run method in ReaderThread::", e);
            e.printStackTrace();
        } finally {
            try {
                reader.close();

                ProcessorRecord eofRecord = new ProcessorRecord();
                eofRecord.setEof(true);
                linkedBlockingQueue.put(eofRecord);
            } catch (Exception e) {
                logger.error("ERROR While reader close method in ReaderThread::", e);
                e.printStackTrace();
            }
        }

    }

	/** Try to fill new additions to db record
	 * @param record received record (read)
	 * @param existingDbRec existing record from database where new data will be added
	 * @return <code>true</code>=added data to existingDbrec, <code>false</code>=no new data (no changes)
	 */
	private boolean fillChanges(CCRecord record, CCRecord existingDbRec) {
		boolean updated = false;
		
		if(existingDbRec.getUrl()==null && record.getUrl()!=null) {
			updated = true;
			existingDbRec.setUrl(record.getUrl());
		}
		
		List<String> curNumberList = record.getNumbers();
		if(curNumberList!=null) {
			if(existingDbRec.getNumbers() == null) {
				updated = true;
				existingDbRec.setNumbers(curNumberList);
			} else {
				for (String number : curNumberList) {
					if(!existingDbRec.getNumbers().contains(number)) {
		    			updated = true;
						existingDbRec.getNumbers().add(number);
					}
				}
			}
		}
		
		if(existingDbRec.getName()==null && record.getName()!=null) {
			updated = true;
			existingDbRec.setName(record.getName());
		}
		if(existingDbRec.getName_confidence()==null && record.getName_confidence()!=null) {
			updated = true;
			existingDbRec.setName_confidence(record.getName_confidence());
		}
		if(existingDbRec.getName_pattern()==null && record.getName_pattern()!=null) {
			updated = true;
			existingDbRec.setName_pattern(record.getName_pattern());
		}
		if(existingDbRec.getJob_title_confidence()==null && record.getJob_title_confidence()!=null) {
			updated = true;
			existingDbRec.setJob_title_confidence(record.getJob_title_confidence());
		}
		if(existingDbRec.getJob_title()==null && record.getJob_title()!=null) {
			updated = true;
			existingDbRec.setJob_title(record.getJob_title());
		}
		if(existingDbRec.getTwitter_confidence()==null && record.getTwitter_confidence()!=null) {
			updated = true;
			existingDbRec.setTwitter_confidence(record.getTwitter_confidence());
		}
		if(existingDbRec.getTwitter_username()==null && record.getTwitter_username()!=null) {
			updated = true;
			existingDbRec.setTwitter_username(record.getTwitter_username());
		}
		if(existingDbRec.getLinkedIn_confidence()==null && record.getLinkedIn_confidence()!=null) {
			updated = true;
			existingDbRec.setLinkedIn_confidence(record.getLinkedIn_confidence());
		}
		if(existingDbRec.getLinkedIn_username()==null && record.getLinkedIn_username()!=null) {
			updated = true;
			existingDbRec.setLinkedIn_username(record.getLinkedIn_username());
		}
		return updated;
	}


    private boolean isRESTClientInitialized() {
        boolean result = false;

        try {
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(properties.getProperty("ES_HOST"),
                                    Integer.parseInt(properties.getProperty("ES_PORT")),
                                    properties.getProperty("ES_URL_SCHEME"))).setRequestConfigCallback(builder -> builder.setConnectionRequestTimeout(0).setConnectTimeout(10000).setSocketTimeout(1000 * 60 * 5)));
            result = true;
        } catch (Exception exception) {
            logger.error("Exception Generated::", exception);
        }
        return result;
    }

    private boolean isSecureRESTClientInitialized() {

        boolean result = false;

        try {

            final CredentialsProvider credentialsProvider =
                    new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials("elastic", "elastic"));

            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(properties.getProperty("ES_HOST"),
                                    Integer.parseInt(properties.getProperty("ES_PORT")),
                                    properties.getProperty("ES_URL_SCHEME"))
                    ).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(
                                HttpAsyncClientBuilder httpClientBuilder) {
                            httpClientBuilder.disableAuthCaching();
                            return httpClientBuilder
                                    .setDefaultCredentialsProvider(credentialsProvider);
                        }
                    })
                            .setRequestConfigCallback(builder -> builder.setConnectionRequestTimeout(0).setConnectTimeout(10000).setSocketTimeout(1000 * 60 * 5))
            );
            result = true;
        } catch (Exception exception) {
            logger.error("Exception Generated::", exception);
        }
        return result;

    }
}
