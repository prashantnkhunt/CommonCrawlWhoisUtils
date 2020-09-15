package com.prominentpixel.tyler.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWrapper;
import com.prominentpixel.tyler.mapper.WrapperToDomain;
import org.codehaus.jettison.json.JSONException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonToPojo {

    public static void main(String args[]) throws IOException, JSONException {

        String d = "{\"references\":{\"l\":[{\"m\":{\"url\":{\"s\":\"http://wag.ca/about/press/media-releases/read,release/263/wag-s-birchwood-bmw-art-soul-one-hundred-celebrates-a-century-of-heroes-sins-and-has-beens-on-february-23\"},\"timestamp\":{\"s\":\"2017-04-27T10:55:27Z\"}}}]},\"domain\":{\"s\":\"mtsallstream.com\"},\"numbers\":{\"sS\":[\"+12049187183\"]},\"name\":{\"m\":{\"confidence\":{\"n\":\"1\"},\"name\":{\"s\":\"Jennifer Nixon\"},\"pattern\":{\"s\":\"first.last\"}}},\"email\":{\"s\":\"jennifer.nixon@mtsallstream.com\"}}\n";
        WrapperToDomain wrapperToDomain = new WrapperToDomain();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "/home/jdz/Desktop/0003b31b-9868-4629-80f3-8278885836f3.txt"));
            String line = reader.readLine();
            while (line != null) {
                ObjectMapper mapper = new ObjectMapper();
                CCRecordWrapper loadingObject = mapper.readValue(line, CCRecordWrapper.class);
                CCRecord record  = wrapperToDomain.convertWrapperToDomain(loadingObject);


                // read next line
                line = reader.readLine();
            }

            System.out.println("done");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
