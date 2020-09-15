package com.prominentpixel.tyler.aws;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.prominentpixel.tyler.dao.whois.WhoIsRecordFull;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LoadWhoisData {

    static String csvPath = "/home/jdz/Downloads/new/tyler/whois-db-download-info-sample.csv";

    public static void main(String[] args) {
        parseWhoisReords(csvPath);
    }


    public static void parseWhoisReords(String csvPath) {

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        DynamoDBMapper dynamoDBMapper= new DynamoDBMapper(client);

        ObjectReader oReader = csvMapper.reader(WhoIsRecordFull.class).with(schema);
        List<WhoIsRecordFull> courses = new ArrayList<>();
        try (Reader reader = new FileReader(new File(csvPath))) {

            MappingIterator<WhoIsRecordFull> mi = oReader.readValues(reader);
            while (mi.hasNext()) {
                WhoIsRecordFull current = mi.next();
                courses.add(current);
                dynamoDBMapper.save(current);
               // System.out.println(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
