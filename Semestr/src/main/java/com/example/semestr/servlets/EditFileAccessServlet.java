package com.example.semestr.servlets;

import com.example.semestr.entities.FileAccess;
import com.example.semestr.entities.FileDC;
import com.example.semestr.exeption.DbException;
import com.example.semestr.exeption.NoFoundRows;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/editfileaccess")
public class EditFileAccessServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;
    private CRUDRepositoryFileAccessImpl repositoryFileAccess;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
        repositoryFileAccess = (CRUDRepositoryFileAccessImpl) getServletContext().getAttribute("repositoryFileAccess");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idFile = Long.valueOf(request.getParameter("idFile"));
        FileDC fileDC = repositoryFile.findById(idFile);

        if (!(SecurityService.isAccess(request, fileDC.getHolderId()))) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/no_access.jsp").forward(request, response);
            return;
        }

        String fileAccessString = repositoryFileAccess.findByFileId(idFile)
                .stream()
                .mapToLong(FileAccess::getUserId)
                .collect(StringBuilder::new, (stringBuilder, aLong) -> stringBuilder.append(" ").append(aLong), StringBuilder::append)
                .toString();

        request.setAttribute("public_access", fileDC.isPublicAccess());
        request.setAttribute("user_access", fileAccessString);
        request.setAttribute("idFile", idFile);

        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/edit_file_access.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean newPublicAccess = request.getParameter("public_access") != null; //== null ? false : true
        String userAccess = request.getParameter("user_access");

        Long idFile = Long.valueOf(request.getParameter("idFile"));
        FileDC oldFile = repositoryFile.findById(idFile);

        FileDC newFileDC = FileDC.builder()
                .id(idFile)
                .title(oldFile.getTitle())
                .description(oldFile.getDescription())
                .holderId(oldFile.getHolderId())
                .nameFile(oldFile.getNameFile())
                .publicAccess(newPublicAccess)
                .build();

        repositoryFile.update(newFileDC);

        String message = "";

        String[] accessUserIdString = userAccess.split("\\D+");
        long[] accessUserIdLong = Arrays.stream(accessUserIdString)
                .filter(str -> !str.isBlank())
                .mapToLong(Long::valueOf).toArray();


        try {
            repositoryFileAccess.deleteByFileId(idFile);

        } catch (NoFoundRows ignored) {
            // TODO: 26.11.2022 отлавливать ошибки при удалении repositoryFileAccess
        }


        for (Long idUser : accessUserIdLong) {
            try {
                repositoryFileAccess.save(idFile, idUser);

            } catch (DbException ignored) {
                // TODO: 26.11.2022 отлавливать ошибки при добавлении repositoryFileAccess
            }
        }

        response.sendRedirect(getServletContext().getContextPath() + "/myfiles");

    }
}
