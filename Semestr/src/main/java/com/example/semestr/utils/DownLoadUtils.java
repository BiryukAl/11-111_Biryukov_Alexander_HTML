package com.example.semestr.utils;

import lombok.SneakyThrows;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DownLoadUtils {
    @SneakyThrows
    public static String getFileName(String agent, String filename) throws UnsupportedEncodingException {
        if (agent.contains("MSIE")) {
            // браузер IE
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // Браузер Firefox
            Base64Encoder base64Encoder = new Base64Encoder();
            filename = "=?utf-82B?" + base64Encoder.encode(filename.getBytes("utf-8"), 0, filename.getBytes("utf-8").length, null) + "?=";
        } else {
            //Другие браузеры
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}