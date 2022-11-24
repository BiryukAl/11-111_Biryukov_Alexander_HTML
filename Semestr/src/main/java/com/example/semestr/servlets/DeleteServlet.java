package com.example.semestr.servlets;

import com.example.semestr.entities.FileDC;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.File;
import java.io.IOException;

import static com.example.semestr.MainContextListener.FULL_UPLOAD_DIRECTORY;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idFile = Long.valueOf(request.getParameter("idFile"));
        String fileName = repositoryFile.findById(idFile).getNameFile();
        repositoryFile.delete(idFile);
        //Сами файлики можно и не удалять хехехе))))
        // TODO: 24.11.2022 Удаление через относительный путь
        File file = new File(FULL_UPLOAD_DIRECTORY + File.separator + fileName);
        // TODO: 24.11.2022 Deleteee
        if (file.delete()){
            request.setAttribute("message", "Delete Done");
        }else {
            request.setAttribute("message", "Delete Error");
        }
        response.sendRedirect(getServletContext().getContextPath() + "/myfiles");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
