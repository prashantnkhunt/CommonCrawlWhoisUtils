package com.prominentpixel.tyler.utils;

/*import com.google.common.net.InternetDomainName;*/
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ESUtils {

    private static Logger logger = Logger.getLogger(ESUtils.class.getName());

    private static void refreshIndices(RestHighLevelClient client, String indexName){

        try{
            RefreshRequest request = new RefreshRequest(indexName);
            RefreshResponse refreshResponse = client.indices().refresh(request, RequestOptions.DEFAULT);

        }catch(ElasticsearchException esException){
            if (esException.status() == RestStatus.NOT_FOUND) {
                logger.error("ElasticSearch index not found.");
            }
        }catch(Exception exception){
            logger.error("Exception Generated::",exception);
        }
    }

    public static void closeHighlevelESClient(RestHighLevelClient client){
        try{
            if(null != client){
                client.close();
            }
        }catch(Exception exception){
            logger.error("Exception Generated::",exception);
        }
    }

    public synchronized static void writeToOutputFile(String line) throws Exception {
        Properties properties = PropertyUtils.loadConfigProperties("config.properties");
        String inputFile = properties.getProperty("OUTPUT_FILE_WAT_PATH");
        Files.write(Paths.get(inputFile),(line).getBytes(), StandardOpenOption.CREATE , StandardOpenOption.APPEND);
        Files.write(Paths.get(inputFile),(System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public synchronized static void writeToFailFile(String line) {
        try {
            Properties properties = PropertyUtils.loadConfigProperties("config.properties");
            String inputFile = properties.getProperty("OUTPUT_FILE_FAILED_WAT_PATH");
            Files.write(Paths.get(inputFile),(line).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            Files.write(Paths.get(inputFile),(System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e){
            logger.error("Error while writing a fail file.");
        }

    }

    public static String replaceHttpWWW(String host){
        if(host!=null) {
            return host.replaceFirst("^((http[s]?://)*www\\.|http[s]?://|www\\.)","");
        } else {
            return null;
        }
    }

    private static Pattern http = Pattern.compile("^(((http)([s])?://)+)");
    private static Pattern p = Pattern.compile("^(((http)([s])?://)+).*");


    public static String replaceHttpMultipleTimeToOneTime(String host){
        Matcher m = http.matcher(host);
        String output = m.replaceFirst("http$4://");
        return output;
    }

    /*public static boolean isSameDomain(String url1, String url2){
        String url1Domain = getDomain(url1);
        String url2Domain = getDomain(url2);
        String url1TopDomain =  getPrivateDomain(url1Domain);
        String url2TopDomain =  getPrivateDomain(url2Domain);
        if(url1TopDomain!=null && url1TopDomain.equalsIgnoreCase(url2TopDomain)){
            return true;
        }else{
            return false;
        }
    }*/
   /* private static String getPrivateDomain(String url1Domain){
        try {
            return InternetDomainName.from(url1Domain).topPrivateDomain().toString();
        }catch (Exception e){
            //logger.error("Problem while getting private domain : " + url1Domain);
            return null;
        }
    }*/

    public static String getDomain(String url){
        Matcher m = p.matcher(url);
        if(!m.matches()){
            url = "http://"+url;
        }
        URL hostURL;
        try {
            hostURL = new URL(url);
        }catch (Exception e){
            e.printStackTrace();
            return url;
        }
        if(hostURL!=null) {
            return hostURL.getHost();
        }else {
            return url;
        }
    }
}
