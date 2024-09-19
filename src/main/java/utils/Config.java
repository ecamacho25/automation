package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
            	 throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load properties file", ex);
        }
        try (InputStream privateInput = Config.class.getClassLoader().getResourceAsStream("config.private.properties")) {
            if (privateInput == null) {
            	throw new RuntimeException("Unable to find config.private.properties");
            }
            properties.load(privateInput);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load config.private.properties file", ex);
        }
    }

    public static String getStringProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static int getIntProperty(String key) {
        String value = properties.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Property " + key + " is not a valid integer", e);
        }
    }
}

