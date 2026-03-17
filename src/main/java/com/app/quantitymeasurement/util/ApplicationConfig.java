package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static Properties prop = new Properties();

    static {

        try {

            InputStream is =
                    ApplicationConfig.class.getClassLoader()
                            .getResourceAsStream("application.properties");

            prop.load(is);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUrl() {
        return prop.getProperty("db.url");
    }

    public static String getUser() {
        return prop.getProperty("db.username");
    }

    public static String getPass() {
        return prop.getProperty("db.password");
    }

    public static String getDriver() {
        return prop.getProperty("db.driver");
    }

    public static int getPoolSize() {
        return Integer.parseInt(prop.getProperty("db.pool.size"));
    }

    public static String getRepoType() {
        return prop.getProperty("repository.type");
    }
}