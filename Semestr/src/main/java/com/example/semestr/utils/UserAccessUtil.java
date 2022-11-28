package com.example.semestr.utils;

import com.example.semestr.entities.FileAccess;

import java.util.Arrays;
import java.util.List;

public class UserAccessUtil {

    public static long[] convertToLong (String userAccess){
        String[] accessUserIdString = userAccess.split("\\D+");
        return Arrays.stream(accessUserIdString)
                .filter(str -> !str.isBlank())
                .mapToLong(Long::valueOf)
                .filter(id -> id > 0)
                .toArray();
    }

    public static String convertToString(List<FileAccess> list){
        return list.stream()
                .mapToLong(FileAccess::getUserId)
                .collect(StringBuilder::new, (stringBuilder, aLong) -> stringBuilder.append(" ").append(aLong), StringBuilder::append)
                .toString();
    }


}
