package com.prominentpixel.tyler.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class PropertyUtils {

    private static Logger logger = Logger.getLogger(PropertyUtils.class.getName());

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            logger.error("Exception Generated::"+fnfe);
        } catch(IOException ioe) {
            logger.error("Exception Generated::",ioe);
        } finally {
            fis.close();
        }
        return prop;
    }

    public static Properties loadConfigProperties(String filePath){
        Properties configProperties = new Properties();
        try {

            InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(filePath);
            configProperties.load(inputStream);
        }
        catch(Exception e){
            System.out.println("Could not load the file");
            logger.error("Exception Generated::",e);
        }
        return configProperties;
    }

    public static String getLogFilePropertyPath(){
        String path="";

        try {

            path = System.getProperty("user.dir")+ File.separator
                    + "src"+ File.separator
                     +"main"+ File.separator
                    +"resources"+ File.separator +
                    "log4j.properties";
        }
        catch(Exception e){
            System.out.println("Could not load the log file");
            logger.error("Exception Generated::",e);
        }
        return path;
    }
}
