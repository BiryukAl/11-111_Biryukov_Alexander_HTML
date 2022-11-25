package com.example.semestr.servlets;

import com.example.semestr.entities.FileDC;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/search_file.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        List<FileDC> filesDC = null;

        if (description.isEmpty()) {
            filesDC = repositoryFile.findByTitle(title);
        } else {
            filesDC = repositoryFile.findByTitleAndDescription(title, description);
        }

        request.setAttribute("items_search_files", filesDC);
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/done_search.jsp").forward(request, response);


    }
}
