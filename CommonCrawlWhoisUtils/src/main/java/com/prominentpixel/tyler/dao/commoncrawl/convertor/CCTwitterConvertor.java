package com.prominentpixel.tyler.dao.commoncrawl.convertor;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordName;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordTwitter;

public class CCTwitterConvertor implements DynamoDBTypeConverter<String, CCRecordTwitter> {

    @Override
    public String convert(CCRecordTwitter object) {
        CCRecordTwitter source = (CCRecordTwitter) object;
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
    public CCRecordTwitter unconvert(String s) {

        CCRecordTwitter result = new CCRecordTwitter();
        try {
            if (s != null && s.length() != 0) {

                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(s, CCRecordTwitter.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
