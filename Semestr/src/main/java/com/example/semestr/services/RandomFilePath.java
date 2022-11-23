package com.example.semestr.services;

import jakarta.servlet.http.Part;

import java.util.Random;

public class RandomFilePath {

    static final int targetStringLength = 10;

    public static String generateFileName(String oldNameFile) {
        final String[] split = oldNameFile.split("\\.");
        final String suffix = split[split.length - 1];
        final String name = getRandomString(targetStringLength) + "." + suffix;
        return name;
    }

    static private String getRandomString(int size){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "testName.txt";
    }

}
