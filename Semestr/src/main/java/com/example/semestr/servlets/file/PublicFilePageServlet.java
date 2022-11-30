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

import static com.example.semestr.MainContextListener.MAX_FILES_FOR_PAGE;

@WebServlet("/main")
public class PublicFilePageServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request, "ProduceDisk");
        String pageStr = request.getParameter("page");
        if (pageStr == null) {
            response.sendRedirect(getServletContext().getContextPath() + "/");
            return;
        }

        Integer page = Integer.valueOf(pageStr);
        Integer shift = Integer.valueOf(request.getParameter("shift") == null ? "0" : request.getParameter("shift"));

        if (shift == 1) {
            shift = -1;
        } else if (shift == 2) {
            shift = 1;
        } else {
            shift = 0;
        }

        page = page + shift;

        request.setAttribute("page", page);

        List<FilesAndNameHolderDC> itemsPublicFiles = repositoryFile.findAllPublicAndNameHolder(MAX_FILES_FOR_PAGE, page - 1);

        boolean is_next = itemsPublicFiles.size() < MAX_FILES_FOR_PAGE;


        request.setAttribute("is_next", is_next ? true : null);

        request.setAttribute("items_public_files", itemsPublicFiles);


        // TODO: 25.11.2022 Вывод Holder Name
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/file_public_page.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
