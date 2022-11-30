package com.example.semestr.servlets.file;

import com.example.semestr.entities.FileDC;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import static com.example.semestr.MainContextListener.FULL_UPLOAD_DIRECTORY;

@WebServlet("/file/delete")
public class DeleteFileServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idFile = Long.valueOf(request.getParameter("idFile"));
        FileDC fileDC = repositoryFile.findById(idFile);

        if (!(SecurityService.isAccess(request, fileDC.getHolderId()))) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/no_access.jsp").forward(request, response);
            return;
        }

        String fileName = fileDC.getNameFile();
        repositoryFile.delete(idFile);
        //Сами файлики можно и не удалять хехехе))))
        // TODO: 24.11.2022 Удаление через относительный путь для звлива на сервер
        File file = new File(FULL_UPLOAD_DIRECTORY + File.separator + fileName);
        if (file.delete()) {
            request.setAttribute("message", "Delete Done");
        } else {
            request.setAttribute("message", "Delete Error");
        }
        response.sendRedirect(getServletContext().getContextPath() + "/myfiles");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
