package com.prominentpixel.tyler.mapreduce;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDynamoDb;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDB;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CCrawlMapper extends Mapper<LongWritable, Text, NullWritable,Text> {

    private AmazonDynamoDB client;
    private ObjectMapper mapper;
    private DynamoDBMapper dynamoDBMapper;
    private DynamoDBMapperConfig dynamoDBMapperConfig;


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
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        context.getCounter(CCrawlDriver.CCrawlCounter.InputRecords).increment(1);
        String line = value.toString();
        try {
            CCRecordDynamoDb loadingObject = mapper.readValue(line, CCRecordDynamoDb.class);

            CCRecordDB dbObject = dynamoDBMapper.load(CCRecordDB.class, loadingObject.getDomain(),dynamoDBMapperConfig);

            //Means the loadingobject domain is first time getting loaded in DB.
            if(null == dbObject){

                CCRecordDB dbStoreObject = new CCRecordDB();

                dbStoreObject.setDomain(loadingObject.getDomain());

                List<CCRecordDynamoDb> emailList = new ArrayList<>();
                emailList.add(loadingObject);
                dbStoreObject.setEmails(emailList);
                dynamoDBMapper.save(dbStoreObject);
                context.getCounter(CCrawlDriver.CCrawlCounter.RecordsInserted).increment(1);
            }
            else { //Means Obj is already present in DB, Need to Update it.

                //Check if both the objects are same, if same, do nothing.
                List<CCRecordDynamoDb> dbRecords = dbObject.getEmails();
                boolean isMatched = false;

                if(null != dbRecords && dbRecords.size() > 0){
                    for(CCRecordDynamoDb dbRecord : dbRecords){

                        if(dbRecord.compareTo(loadingObject) == 0){
                            isMatched = true;
                            break;
                        }
                    }
                    if(!isMatched){ // Means the EmailRecord is not present in DB.
                        dbRecords.add(loadingObject);
                        dbObject.setEmails(dbRecords);
                        dynamoDBMapper.save(dbObject);
                        context.getCounter(CCrawlDriver.CCrawlCounter.UpdatedRecords).increment(1);
                    }

                }

            }

           // context.write(NullWritable.get(),new Text(line));
        }catch(UnrecognizedPropertyException upe){
            //If any unmapped filed is found we need to track those records..

            context.write(NullWritable.get(),new Text(line));
            context.getCounter(CCrawlDriver.CCrawlCounter.UnParsedRecords).increment(1);

        }catch(AmazonDynamoDBException dde){
            context.write(NullWritable.get(),new Text(line));
            context.getCounter(CCrawlDriver.CCrawlCounter.LimitExceddedRecords).increment(1);
        }
    }
}
