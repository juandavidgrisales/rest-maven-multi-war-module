package com.payu.util.properties;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {



    @SuppressWarnings("unused")
    public static Properties getProperties(String ClassName, String propertiesName){
        InputStream ip = null;

        try {

            Properties props = new Properties();
            ip = Class.forName(ClassName).getClassLoader()
                    .getResourceAsStream("/"+propertiesName);
            if (ip == null) {
                ip = Class.forName(ClassName).getClass()
                        .getResourceAsStream("/"+propertiesName);
            }
            if (ip == null) {
                ip = new FileInputStream("./"+propertiesName);
            }
            if (ip == null) {
                ip = new FileInputStream(propertiesName);
            }
            props.load(ip);

            return props;
        } catch (Exception e) {
            Logger.getRootLogger().fatal("Cannot get the properties", e);
            return null;
        } finally {
            try {
                ip.close();
            } catch (Exception ignored) {
            }
        }
    }

    @SuppressWarnings("unused")
    public static Properties getProperties(String propertiesName){
        InputStream ip = null;

        try {

            Properties props = new Properties();
            ip = new FileInputStream("./"+propertiesName);
            if (ip == null) {
                ip = new FileInputStream(propertiesName);
            }
            props.load(ip);

            return props;
        } catch (Exception e) {
            Logger.getRootLogger().fatal("Cannot get the properties", e);
            return null;
        } finally {
            try {
                ip.close();
            } catch (Exception ignored) {
            }
        }
    }

}

