package com.example.semestr;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static Properties getProperties(){

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("resources/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

}
