package com.example.semestr.servlets;

import com.example.semestr.entities.FileDC;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/editfile")
public class EditFileServlet extends HttpServlet {


    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("idFile", request.getParameter("idFile"));
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/edit_file.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean publicAccess = request.getParameter("public_access") != null; //== null ? false : true

        Long idFile = Long.valueOf(request.getParameter("idFile"));

        FileDC oldFile =  repositoryFile.findById(idFile);
        FileDC newFileDC = FileDC.builder()
                .id(idFile)
                .title(title)
                .description(description)
                .holderId(oldFile.getHolderId())
                .nameFile(oldFile.getNameFile())
                .publicAccess(publicAccess)
                .build();

        repositoryFile.update(newFileDC);

        response.sendRedirect(getServletContext().getContextPath() + "/myfiles");
    }
}
