package com.example.semestr.services;

import java.util.Random;

public class RandomFilePath {

    static final int targetStringLength = 10;

    public static String generateFileName(String oldNameFile) {
        final String[] split = oldNameFile.split("\\.");
        final String suffix = split[split.length - 1];
        final String newName = getRandomString(targetStringLength) + "." + suffix;
        return newName;
    }

    static private String getRandomString(int size) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}
