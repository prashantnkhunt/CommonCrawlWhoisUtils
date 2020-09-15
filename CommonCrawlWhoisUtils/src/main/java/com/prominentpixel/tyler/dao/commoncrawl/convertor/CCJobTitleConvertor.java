package com.prominentpixel.tyler.dao.commoncrawl.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCJobTitle;

public class CCJobTitleConvertor implements DynamoDBTypeConverter<String, CCJobTitle> {

    @Override
    public String convert(CCJobTitle object) {
        CCJobTitle source = (CCJobTitle) object;
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
    public CCJobTitle unconvert(String s) {

        CCJobTitle result = new CCJobTitle();
        try {
            if (s != null && s.length() != 0) {

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(s, CCJobTitle.class);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
