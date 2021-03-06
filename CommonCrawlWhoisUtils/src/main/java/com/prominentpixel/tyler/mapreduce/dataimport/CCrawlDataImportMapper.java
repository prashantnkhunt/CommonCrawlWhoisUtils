package com.prominentpixel.tyler.mapreduce.dataimport;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDB;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWrapper;
import com.prominentpixel.tyler.mapper.WrapperToDomain;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CCrawlDataImportMapper extends Mapper<LongWritable, Text, NullWritable,Text> {

    private AmazonDynamoDB client;
    private ObjectMapper mapper;
    private DynamoDBMapper dynamoDBMapper;
    private DynamoDBMapperConfig dynamoDBMapperConfig;
    private WrapperToDomain wrapperToDomain = new WrapperToDomain();



    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        Configuration configuration = context.getConfiguration();

        String dynamoDBEndPoint = configuration.get("DynamoDBEndPoint");
        String dynamoDBRegion = configuration.get("DynamoDBRegion");

        client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBEndPoint, dynamoDBRegion))
                .build();

        dynamoDBMapper= new DynamoDBMapper(client);

        //TODO:Initial load with Consistent READ then use Default.
        dynamoDBMapperConfig = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);

        mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        context.getCounter(CCCrawlDataImportDriver.CCrawlImportCounter.InputRecords).increment(1);
        String line = value.toString();
        try {

            CCRecordWrapper loadingObjectFromInput = mapper.readValue(line, CCRecordWrapper.class);
            CCRecord loadingObject  = wrapperToDomain.convertWrapperToDomainWhoIsEmails(loadingObjectFromInput);

            CCRecordDB dbObject = dynamoDBMapper.load(CCRecordDB.class, loadingObject.getDomain(),dynamoDBMapperConfig);

            //Means the loadingobject domain is first time getting loaded in DB.
            if(null == dbObject){

                CCRecordDB dbStoreObject = new CCRecordDB();

                dbStoreObject.setDomain(loadingObject.getDomain());

                List<CCRecord> emailList = new ArrayList<>();
                emailList.add(loadingObject);
                dbStoreObject.setEmails(emailList);
                dynamoDBMapper.save(dbStoreObject);
                context.getCounter(CCCrawlDataImportDriver.CCrawlImportCounter.RecordsInserted).increment(1);
            }
            else { //Means Obj is already present in DB, Need to Update it.

                //Check if both the objects are same, if same, do nothing.
                List<CCRecord> dbRecords = dbObject.getEmails();
                boolean isMatched = false;

                if(null != dbRecords && dbRecords.size() > 0){
                    for(CCRecord dbRecord : dbRecords){

                        if(dbRecord.compareTo(loadingObject) == 0){
                            isMatched = true;
                            break;
                        }
                    }
                    if(!isMatched){ // Means the EmailRecord is not present in DB.
                        dbRecords.add(loadingObject);
                        dbObject.setEmails(dbRecords);
                        dynamoDBMapper.save(dbObject);
                        context.getCounter(CCCrawlDataImportDriver.CCrawlImportCounter.UpdatedRecords).increment(1);
                    }
                }
            }

            // context.write(NullWritable.get(),new Text(line));
        }catch(UnrecognizedPropertyException upe){
            //If any unmapped filed is found we need to track those records..

            context.write(NullWritable.get(),new Text(line));
            context.getCounter(CCCrawlDataImportDriver.CCrawlImportCounter.UnParsedRecords).increment(1);

        }catch(AmazonDynamoDBException dde){
            context.write(NullWritable.get(),new Text(line));
            context.getCounter(CCCrawlDataImportDriver.CCrawlImportCounter.LimitExceddedRecords).increment(1);
        }
    }
}
