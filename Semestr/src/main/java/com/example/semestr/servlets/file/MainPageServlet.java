package com.example.semestr.servlets.file;

import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.DecorationPages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.example.semestr.MainContextListener.MAX_FILES_FOR_PAGE;

@WebServlet("/")
public class MainPageServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request, "ProduceDisk");
        request.setAttribute("count_file", repositoryFile.countFiles());
        request.setAttribute("items_public_files", repositoryFile.findAllPublicAndNameHolder(MAX_FILES_FOR_PAGE, 0));
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/main_page.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
