package com.example.semestr.servlets;

import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
//        repositoryUser= (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("items_public_files", repositoryFile.findAllPublic()); // Можно прикрутить страницы
        // TODO: 25.11.2022 Вывод Holder Name
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/main_page.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
