package com.example.semestr.servlets.file;

import com.example.semestr.entities.FilesAndNameHolderDC;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.DecorationPages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/files/search")
public class SearchServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request, "Search Files");
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/search_file.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        DecorationPages.setTitle(request, "Search:" + title + " " + description);

        List<FilesAndNameHolderDC> files = null;

        if (title == null) {
            title = "";
        }
        if (description == null) {
            description = "";
        }

        title = title.toLowerCase();
        description = description.toLowerCase();

        if (!(title.isEmpty() && description.isEmpty())) {
            files = repositoryFile.findByNameHolderTitleAndDescription(title, description);
        }

        request.setAttribute("title", title);
        request.setAttribute("description", description);

        request.setAttribute("items_public_files", files);
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/search_file.jsp").forward(request, response);
    }
}
