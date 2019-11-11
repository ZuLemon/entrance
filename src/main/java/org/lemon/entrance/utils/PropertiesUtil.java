package org.lemon.entrance.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public String getValue(String key) {
        Properties props = new Properties();
        InputStream in;
        try{
            in=getClass().getResourceAsStream("/server.properties");
            props.load(in);
        } catch(Exception e) {
            LoggerUtils.error(e.getMessage());
            return null;
        }
        if(props.isEmpty()) {
            return null;
        }
       return props.get(key).toString();
    }
}