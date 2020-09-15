package com.prominentpixel.tyler.dao.commoncrawl.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordLinkedin;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordTwitter;

public class CCLinkedinsConvertor implements DynamoDBTypeConverter<String, CCRecordLinkedin> {

    @Override
    public String convert(CCRecordLinkedin object) {
        CCRecordLinkedin source = (CCRecordLinkedin) object;
        String result = null;
        try {
            if (source != null) {
                result = source.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public CCRecordLinkedin unconvert(String s) {

        CCRecordLinkedin result = new CCRecordLinkedin();
        try {
            if (s != null && s.length() != 0) {

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(s, CCRecordLinkedin.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
