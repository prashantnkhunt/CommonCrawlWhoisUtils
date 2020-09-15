package com.prominentpixel.tyler.dao.commoncrawl.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordName;

public class CCRecordNameConvertor implements DynamoDBTypeConverter<String, CCRecordName> {

    @Override
    public String convert(CCRecordName object) {
        CCRecordName source = (CCRecordName) object;
        String ccRecordName = null;
        try {
            if (source != null) {
                ccRecordName = source.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ccRecordName;
    }

    @Override
    public CCRecordName unconvert(String s) {

        CCRecordName result = new CCRecordName();
        try {
            if (s != null && s.length() != 0) {

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(s, CCRecordName.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
