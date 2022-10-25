package workingHTTP;

import java.io.*;
import java.util.Random;

public class MyDownloader {



    //todo - показать пример с Closeable
    public static void writeToFile(InputStream response, String fileName) throws IOException {
        final File file = new File(fileName);
        OutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = response.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        //что будет если не вызвать close - домашка
        outStream.close();
    }
    public static String generateImageName(String imageUrl) {
        final String[] split = imageUrl.split("\\.");
        final String suffix = split[split.length - 1];
        final String name = generateString() + "." + suffix;
        return name;
    }


    public static String generateString()
    {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
