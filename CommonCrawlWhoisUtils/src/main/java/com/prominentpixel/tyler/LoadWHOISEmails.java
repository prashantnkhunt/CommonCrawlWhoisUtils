package com.prominentpixel.tyler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecord;
import com.prominentpixel.tyler.dao.commoncrawl.CCRecordDynamoDb;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWhoisWrapper;
import com.prominentpixel.tyler.dao.commoncrawl.dataimport.CCRecordWrapper;
import com.prominentpixel.tyler.mapper.WrapperToDomain;
import com.prominentpixel.tyler.processor.ReaderWriter;
import com.prominentpixel.tyler.utils.PropertyUtils;

public class LoadWHOISEmails {
    private static final Logger logger = Logger.getLogger(LoadWHOISEmails.class);

    private static Properties properties = null;
	/*    private static BufferedReader reader;
	private static ObjectMapper mapper = new ObjectMapper();
	private static RestHighLevelClient client = null;*/
    private static final String SEPARATOR = System.getProperty("file.separator");

    public static void main(String... args) throws Exception {

        properties = PropertyUtils.loadConfigProperties("config.properties");
        String inputDirectoryPath = properties.getProperty("INPUT_DIRECTORY_PATH");
        String outputFile = properties.getProperty("OUTPUT_FILE_WAT_PATH");

        Integer noOfThread = Integer.parseInt(properties.getProperty("BULK_PROCESS_NO_OF_THREAD"));

        List<String> filesPath = Arrays.asList(new File(inputDirectoryPath).list());

        File file = new File(outputFile);
        if(!file.exists()){
            file.createNewFile();
        }
        List<String> outputFilePah = Files.readAllLines(Paths.get(outputFile));

        filesPath.removeAll(outputFilePah);

        Semaphore semaphore = new Semaphore(noOfThread);

        for(String filePath : filesPath) {
            semaphore.acquire(1);
            new ReaderWriter( inputDirectoryPath+SEPARATOR+filePath,semaphore);
        }

        /*properties = PropertyUtils.loadConfigProperties("config.properties");
        isRESTClientInitialized();
        run();
        System.out.println("done");*/
    }
	
    public static void testLocal1() {
        WrapperToDomain wrapperToDomain = new WrapperToDomain();

        Set<String> domains = new HashSet<>();
        BufferedReader reader;
        try {
            //            /home/Desktop/0003b31b-9868-4629-80f3-8278885836f3.txt
            reader = new BufferedReader(new FileReader("/home/pp-4/Desktop/Work/Docs/whois/BCUP/whois/1-whois-emails"));
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            String line = reader.readLine();
            int count=0;
            while (line != null) {
                count++;
                CCRecordWhoisWrapper loadingObject = mapper.readValue(line, CCRecordWhoisWrapper.class);
                CCRecord record  = wrapperToDomain.convertWrapperToDomainWhoIsEmails(loadingObject);
                
                String domain = record.getDomain();
                String email = record.getEmail();
                
//                if(domains.add(domain))
//					System.out.println(domain);
                //isRecordExistsByDomain
//                record.getDomain()
                
                // read next line
                line = reader.readLine();
            }
            System.out.println("done" + count);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                CCRecordDynamoDb record  = wrapperToDomain.convertWrapperToDomainWhoIsEmailsDynamoDB(loadingObject);
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
