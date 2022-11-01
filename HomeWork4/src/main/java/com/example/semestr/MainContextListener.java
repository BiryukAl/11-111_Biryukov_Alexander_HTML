package com.example.semestr;


import com.example.semestr.jdbc.MyDataSource;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.PreparedStatement;
import java.util.Properties;

@WebListener
public class MainContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        Properties properties = Main.getProperties();
        try {
            sce.getServletContext()
                    .setAttribute("datasource",new CRUDRepositoryUserImpl(
                            new MyDataSource(properties.getProperty("db.url")
                                    ,properties.getProperty("db.username")
                                    ,properties.getProperty("db.password"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
