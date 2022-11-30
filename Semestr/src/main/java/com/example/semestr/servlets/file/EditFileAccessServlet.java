package com.example.semestr.servlets.file;

import com.example.semestr.entities.FileDC;
import com.example.semestr.exceptions.DbException;
import com.example.semestr.exceptions.NoFoundRows;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.DecorationPages;
import com.example.semestr.services.SecurityService;
import com.example.semestr.utils.UserAccessUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/file/edit/access")
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

        DecorationPages.setTitle(request, "Edit: " + fileDC.getTitle());


        if (!(SecurityService.isAccess(request, fileDC.getHolderId()))) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/no_access.jsp").forward(request, response);
            return;
        }

        String fileAccessString = UserAccessUtil.convertToString(repositoryFileAccess.findByFileId(idFile));

        request.setAttribute("public_access", fileDC.isPublicAccess() ? true : null);
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

        long[] accessUserIdLong = UserAccessUtil.convertToLong(userAccess);

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

        response.sendRedirect(getServletContext().getContextPath() + "/files/my");

    }
}
