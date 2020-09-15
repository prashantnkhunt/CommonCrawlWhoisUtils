package com.prominentpixel.tyler.mapreduce.contactmerge;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDB;
import com.prominentpixel.tyler.mapreduce.CCrawlDriver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CCrawlContactMergeMapper extends Mapper<LongWritable, Text, NullWritable,Text> {

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

        dynamoDBMapper = new DynamoDBMapper(client);

        //TODO:Initial load with Consistent READ then use Default.
        dynamoDBMapperConfig = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);

        mapper = new ObjectMapper();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        context.getCounter(CCrawlDriver.CCrawlCounter.InputRecords).increment(1);

        if(null != value && !value.toString().isEmpty()){

            String line = value.toString();

            if(line.contains(",")){

                try {

                    String[] lineValues = line.split(",");
                    String contactURL = lineValues[0];
                    String domain = lineValues[1];

                    if(null != domain && !domain.isEmpty()){

                        CCRecordDB dbObject = dynamoDBMapper.load(CCRecordDB.class, domain, dynamoDBMapperConfig);

                        //Means the loadingobject domain is first time getting loaded in DB.
                        if (null == dbObject) {

                            CCRecordDB dbStoreObject = new CCRecordDB();

                            dbStoreObject.setDomain(domain);
                            dbStoreObject.setContactURL(contactURL);

                            dynamoDBMapper.save(dbStoreObject);
                            context.getCounter(CCrawlContactMergeDriver.CCrawlCounter.RecordsInserted).increment(1);

                        } else { //Means Obj is already present in DB, Need to Update it.

                            dbObject.setContactURL(contactURL);
                            dynamoDBMapper.save(dbObject);
                            context.getCounter(CCrawlContactMergeDriver.CCrawlCounter.UpdatedRecords).increment(1);
                        }

                    }
                }catch (AmazonDynamoDBException dde) {
                    context.write(NullWritable.get(), new Text(line));
                    context.getCounter(CCrawlContactMergeDriver.CCrawlCounter.LimitExceddedRecords).increment(1);
                }
            }
        }
    }
}
