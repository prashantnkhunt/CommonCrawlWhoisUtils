package com.prominentpixel.tyler.dao.commoncrawl.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCJobTitle;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;

public class CCRecordConvertor implements DynamoDBTypeConverter<String, CCRecord> {

    @Override
    public String convert(CCRecord object) {
        CCRecord source = (CCRecord) object;
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
    public CCRecord unconvert(String s) {

        CCRecord result = new CCRecord();
        try {
            if (s != null && s.length() != 0) {

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(s, CCRecord.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
