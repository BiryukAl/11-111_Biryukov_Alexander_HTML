package com.example.semestr.servlets.file;

import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.DecorationPages;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/main")
public class PublicFilePageServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
//        repositoryUser= (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request,"ProduceDisk");
        String pageStr = request.getParameter("page");
        if (pageStr == null){
            response.sendRedirect(getServletContext().getContextPath() + "/");
            return;
        }

        Integer page = Integer.valueOf(pageStr);
        Integer sift = Integer.valueOf(request.getParameter("shift"));

        page = page - sift;

        request.setAttribute("page", page);

        request.setAttribute("items_public_files", repositoryFile.findAllPublicAndNameHolder(10, page-1)); // Можно прикрутить страницы


        // TODO: 25.11.2022 Вывод Holder Name
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/file_public_page.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
