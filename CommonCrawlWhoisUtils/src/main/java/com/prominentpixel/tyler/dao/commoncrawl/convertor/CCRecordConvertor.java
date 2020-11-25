package com.prominentpixel.tyler.dao.commoncrawl.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCJobTitle;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDynamoDb;

public class CCRecordConvertor implements DynamoDBTypeConverter<String, CCRecordDynamoDb> {

    @Override
    public String convert(CCRecordDynamoDb object) {
        CCRecordDynamoDb source = (CCRecordDynamoDb) object;
        String ccRecordName = null;
        try {
            if (source != null) {
                ccRecordName = source.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ccRecordName;
    }

    @Override
    public CCRecordDynamoDb unconvert(String s) {

        CCRecordDynamoDb result = new CCRecordDynamoDb();
        try {
            if (s != null && s.length() != 0) {

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(s, CCRecordDynamoDb.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
