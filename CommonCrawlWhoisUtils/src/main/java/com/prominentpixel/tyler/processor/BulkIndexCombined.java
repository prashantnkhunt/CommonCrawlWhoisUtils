package com.prominentpixel.tyler.processor;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.utils.ESUtils;
import com.prominentpixel.tyler.utils.PropertyUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BulkIndexCombined {

    private static Logger logger = Logger.getLogger(BulkIndexCombined.class.getName());

    private RestHighLevelClient client = null;
    private Properties properties = null;
    private BulkProcessor bulkProcessor = null;
    private ObjectMapper mapper = new ObjectMapper();
    private List<String> filterExtensionList;
    private String indexName;
    private String fileName;
    private BulkProcessorActionListener bulkProcessorActionListener;

    public BulkIndexCombined(String fileName) {
        this.fileName = fileName;
        init();
    }

    private void init() {

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
                if (isBulkProcessorInitialized()) {

                } else {
                    logger.error("ElasticSearch Bulk Processor is not initialized properly.");
                }
            } else {
                logger.error("ElasticSearch is not initialized properly.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("Exception Generated::", exception);
        }
    }

    public void indexData(ProcessorRecord processorRecord) throws Exception {
		IndexRequest request = new IndexRequest();
		request = request.index(indexName);
		if(processorRecord.getId() != null)
			request = request.id(processorRecord.getId());

       /* bulkProcessor.add(new IndexRequest(indexName).id(processorRecord.getCcRecordDB().getDomain())
                .source(mapper.writeValueAsString(processorRecord.getCcRecordDB()), XContentType.JSON));*/

		String sourceData = mapper.writeValueAsString(processorRecord.getCcRecord());
        request = request.source(sourceData, XContentType.JSON);
		
//        if(logger.isDebugEnabled())
//			logger.debug("Save, Id: "+processorRecord.getId()+", record: " + sourceData);
//        System.out.println("Save, Id: "+processorRecord.getId()+", record: " + sourceData);

        bulkProcessor.add(request);
    }

    public void commitIndex() {
        try {
            bulkProcessor.awaitClose(5L, TimeUnit.MINUTES);
            ESUtils.closeHighlevelESClient(client);
        } catch (Exception exception) {
            logger.error("Exception Generated::", exception);
        }
    }

    private boolean isBulkProcessorInitialized() {

        boolean result = false;
        try {
            bulkProcessorActionListener = new BulkProcessorActionListener(fileName);

            BulkProcessor.Builder bulkProcessorBuilder = BulkProcessor.builder(
                    (request, bulkListener) ->
                            client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                    bulkProcessorActionListener);

            /*Set when to flush a new bulk request based on the number of actions currently added (defaults to 1000,
            use -1 to disable it)*/
            bulkProcessorBuilder.setBulkActions(Integer.parseInt(properties.getProperty("BULK_REQUEST_BATCH_SIZE_COUNT")));

            /*Set when to flush a new bulk request based on the size of actions currently added
            (defaults to 5Mb, use -1 to disable it)*/
            bulkProcessorBuilder.setBulkSize(new ByteSizeValue(
                    Long.parseLong(properties.getProperty("BULK_REQUEST_BATCH_SIZE_MB")),
                    ByteSizeUnit.MB));
            /*Set the number of concurrent requests allowed to be executed (default to 1,
            use 0 to only allow the execution of a single request)*/
            bulkProcessorBuilder.setConcurrentRequests(10);


            /*Set a flush interval flushing any BulkRequest pending if the interval passes (defaults to not set)*/
            // bulkProcessorBuilder.setFlushInterval(TimeValue.timeValueSeconds(20L));

            /*Set a constant back off policy that initially waits for 1 second and retries up to 3 times.
            See BackoffPolicy.noBackoff(), BackoffPolicy.constantBackoff()
            and BackoffPolicy.exponentialBackoff() for more options.*/
            bulkProcessorBuilder.setBackoffPolicy(BackoffPolicy
                    .constantBackoff(TimeValue.timeValueSeconds(1L), 3));

            bulkProcessor = bulkProcessorBuilder.build();

            result = true;
        } catch (Exception exception) {
            logger.error("Exception Generated::", exception);
        }
        return result;
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

    public int getFailedCount() {
        return bulkProcessorActionListener.getFailedCount();
    }
}
