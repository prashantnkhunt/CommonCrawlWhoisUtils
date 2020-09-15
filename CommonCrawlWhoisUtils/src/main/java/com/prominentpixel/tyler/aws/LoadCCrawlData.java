package com.prominentpixel.tyler.aws;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDB;

public class LoadCCrawlData {

    public static void main(String[] args) {

        String ccFilePath = "/home/jdz/Downloads/new/tyler/segment-4-part-m-00008.csv";

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://dynamodb.us-east-1.amazonaws.com us-east-1", "us-west-2"))
                .build();

        String line = "";
        String cvsSplitBy = ",";
        //List<CCRecord> records = new ArrayList<>(1000);
        List<String> unParsedRecords = new ArrayList<>(100);
        List<CCRecord> updatedRecords = new ArrayList<>(100);

        ObjectMapper mapper = new ObjectMapper();

        String csvFile = "/home/jdz/Downloads/new/tyler/Segment-4-part-m-00008.csv";
        BufferedReader br = null;
        DynamoDBMapper dynamoDBMapper= new DynamoDBMapper(client);

        //Initial load with Consistent READ then use Default.
        DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                try {
                    CCRecord loadingObject = mapper.readValue(line, CCRecord.class);

                    CCRecordDB dbObject = dynamoDBMapper.load(CCRecordDB.class, loadingObject.getDomain(),dynamoDBMapperConfig);

                    //Means the loadingobject domain is first time getting loaded in DB.
                    if(null == dbObject){

                        CCRecordDB dbStoreObject = new CCRecordDB();

                        dbStoreObject.setDomain(loadingObject.getDomain());

                        List<CCRecord> emailList = new ArrayList<>();
                        emailList.add(loadingObject);
                        dbStoreObject.setEmails(emailList);
//                        dynamoDBMapper.save(dbStoreObject);
                    }
                    else { //Means Obj is already present in DB, Need to Update it.

                        //Check if both the objects are same, if same, do nothing.
                        List<CCRecord> dbRecords = dbObject.getEmails();
                        boolean isMatched = false;
                        for(CCRecord dbRecord : dbRecords){

                            if(dbRecord.compareTo(loadingObject) == 0){
                                isMatched = true;
                                break;
                            }
                        }
                        if(!isMatched){ // Means the EmailRecord is not present in DB.
                            dbRecords.add(loadingObject);
                            dbObject.setEmails(dbRecords);
//                            dynamoDBMapper.save(dbObject);
                            updatedRecords.add(loadingObject);
                        }
                    }
                }catch(UnrecognizedPropertyException upe){
                    //If any unmapped filed is found we need to track those records..
                    unParsedRecords.add(line);
                    System.out.println("UnParsed Records::"+line);
                    continue;
                }
            }

            System.out.println("UpdatedRecordsCount::"+updatedRecords.size());
            System.out.println("DB Object Present::"+updatedRecords.toString());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }
}
