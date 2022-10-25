package workingHTTP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import static workingHTTP.MyDownloader.generateImageName;
import static workingHTTP.MyDownloader.writeToFile;


public class HttpClientMain {


    public void loadPageToFile(final String url,
                               final String query,
                               final String pageName) {
        URLConnection connection = null;
        try {
            //прокинули запрос
            connection = new URL(url + "?" + query).openConnection();
            //возвращается обратно страница (содержимое)
            InputStream response = connection.getInputStream();
            //считываем содержимое в файл
            writeToFile(response, pageName);
            response.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void loadPostPageToFile(final String url,
                               final String pageName) {
        HttpURLConnection connection = null;
        try {
            //прокинули запрос
            connection = (HttpURLConnection)new URL(url).openConnection();


            //Выбераем способ связи пост запросами
            connection.setRequestMethod("POST");
            //возвращается обратно страница (содержимое)
            InputStream response = connection.getInputStream();
            //считываем содержимое в файл
            writeToFile(response, pageName);
            response.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void loadPost2PageToFile(final String url,
                                   final String pageName) {
        URLConnection connection = null;
        try {
            //прокинули запрос
            connection = new URL(url).openConnection();


            //Выбераем способ связи пост запросами
            connection.setDoOutput(true); // Triggers POST.
            //возвращается обратно страница (содержимое)
            InputStream response = connection.getInputStream();
            //считываем содержимое в файл
            writeToFile(response, pageName);
            response.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }




    public void loadImageToRandomFile(String imageUrl) {
        URLConnection connection = null;
        try {
            connection = new URL(imageUrl).openConnection();
            InputStream response = connection.getInputStream();
            writeToFile(response, generateImageName(imageUrl));
            response.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}
