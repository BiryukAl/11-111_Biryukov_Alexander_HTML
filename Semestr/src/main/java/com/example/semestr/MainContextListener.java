package com.example.semestr;


import com.example.semestr.jdbc.MyDataSource;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.Properties;

@WebListener
public class MainContextListener implements ServletContextListener {

    private final String UPLOAD_DIRECTORY = "WEB-INF/upload/";

    @Override
    public void contextInitialized(ServletContextEvent sce) {


//        Properties properties = Main.getProperties();
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            sce.getServletContext()
                    .setAttribute("datasource", new CRUDRepositoryUserImpl(
                                    new MyDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "qwerty")
                            )
                    );
//                  properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password")
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // TODO: 22.11.2022 getAttribute("user_login") можно это убрать и удет это в одной папке, но надо контекст лисенер засунуть
        String uploadPath = UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        sce.getServletContext().setAttribute("uploadPath", uploadPath);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
