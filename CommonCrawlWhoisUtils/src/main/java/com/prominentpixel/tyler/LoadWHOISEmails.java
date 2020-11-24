package com.prominentpixel.tyler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWrapper;
import com.prominentpixel.tyler.mapper.WrapperToDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadWHOISEmails {

    public static void main(String[] args) {




    }

    private static void testLocal(){
        //        String d = "{\"references\":{\"l\":[{\"m\":{\"url\":{\"s\":\"http://wag.ca/about/press/media-releases/read,release/263/wag-s-birchwood-bmw-art-soul-one-hundred-celebrates-a-century-of-heroes-sins-and-has-beens-on-february-23\"},\"timestamp\":{\"s\":\"2017-04-27T10:55:27Z\"}}}]},\"domain\":{\"s\":\"mtsallstream.com\"},\"numbers\":{\"sS\":[\"+12049187183\"]},\"name\":{\"m\":{\"confidence\":{\"n\":\"1\"},\"name\":{\"s\":\"Jennifer Nixon\"},\"pattern\":{\"s\":\"first.last\"}}},\"email\":{\"s\":\"jennifer.nixon@mtsallstream.com\"}}\n";
        WrapperToDomain wrapperToDomain = new WrapperToDomain();

        BufferedReader reader;
        try {
            //            /home/Desktop/0003b31b-9868-4629-80f3-8278885836f3.txt
            reader = new BufferedReader(new FileReader(
                    "/home/jdz/Desktop/0003b31b-9868-4629-80f3-8278885836f3.txt"));
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            String line = reader.readLine();
            int count=0;
            while (line != null) {
                count++;
                CCRecordWrapper loadingObject = mapper.readValue(line, CCRecordWrapper.class);
                CCRecord record  = wrapperToDomain.convertWrapperToDomainWhoIsEmails(loadingObject);
                // read next line
                line = reader.readLine();
            }
            System.out.println("done" + count);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
