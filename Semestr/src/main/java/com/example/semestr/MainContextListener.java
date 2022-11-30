package com.example.semestr;


import com.example.semestr.jdbc.MyDataSource;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MainContextListener implements ServletContextListener {

//    public static final String UPLOAD_DIRECTORY = "upload/";
//    public static final String FULL_UPLOAD_DIRECTORY = "/home/userlin/web_servers/glassfish6/glassfish/domains/domain1/generated/jsp/Semestr-1.0-SNAPSHOT/upload/";
    public static final String FULL_UPLOAD_DIRECTORY = "/home/userlin/web_servers/upload_server/";
    public static final Integer MAX_FILES_FOR_PAGE = 10;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        MyDataSource myDataSource = new MyDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "qwerty");

        try {
            sce.getServletContext().setAttribute("repositoryUser", new CRUDRepositoryUserImpl(myDataSource));
            sce.getServletContext().setAttribute("repositoryFile", new CRUDRepositoryFileImpl(myDataSource));
            sce.getServletContext().setAttribute("repositoryFileAccess", new CRUDRepositoryFileAccessImpl(myDataSource));
            sce.getServletContext().setAttribute("repositoryFriends", new CRUDRepositoryFriendsImpl(myDataSource));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
