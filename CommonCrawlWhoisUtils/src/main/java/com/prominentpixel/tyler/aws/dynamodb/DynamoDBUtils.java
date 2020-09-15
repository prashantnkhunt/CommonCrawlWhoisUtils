package com.prominentpixel.tyler.aws.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DynamoDBUtils {

    public static void writeCountersToDynamoDB(Long totalCount,String dynamoDBEndpoint,String awsRegion,
                                               String dynamoDbTableName) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint,awsRegion)).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(dynamoDbTableName);

        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
        expressionAttributeNames.put("#D", "date");
        expressionAttributeNames.put("#T", "totalEmails");

        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
        expressionAttributeValues.put(":val1", new Date().toString());
        expressionAttributeValues.put(":val2", totalCount);   //Price
        expressionAttributeValues.put(":default", 0);   //Price expressionAttributeValues.put(":val2", totalCount);   //Price

        UpdateItemOutcome outcome =  table.updateItem(
                "domain",          // key attribute name
                "counter",           // key attribute value
                "SET #D = :val1, #T = if_not_exists(#T,:default) + :val2 ", // UpdateExpression
                expressionAttributeNames,
                expressionAttributeValues);

    }
}
