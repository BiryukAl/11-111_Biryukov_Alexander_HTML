package com.example.semestr.servlets.file;

import com.example.semestr.MainContextListener;
import com.example.semestr.entities.FileAccess;
import com.example.semestr.entities.FileDC;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@WebServlet("/file/download")
public class DownloadFileServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;
    private CRUDRepositoryFileAccessImpl repositoryFileAccess;


    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
        repositoryFileAccess = (CRUDRepositoryFileAccessImpl) getServletContext().getAttribute("repositoryFileAccess");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long idFile = Long.valueOf(request.getParameter("idFile"));
        FileDC fileDC = repositoryFile.findById(idFile);

        boolean access = SecurityService.isAccess(request, fileDC.getHolderId());

        if (!fileDC.isPublicAccess() || !access) {
            List<FileAccess> userAccessId = repositoryFileAccess.findByFileId(fileDC.getId());

            for (FileAccess fileAccess : userAccessId) {
                if (SecurityService.isAccess(request, fileAccess.getUserId())) {
                    access = true;
                    break;
                }
            }
        }

        if (!access && !fileDC.isPublicAccess()) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/no_access.jsp")
                    .forward(request, response);
            return;
        }

        String fileName = fileDC.getNameFile();

        String realPath = MainContextListener.FULL_UPLOAD_DIRECTORY;
        FileInputStream file = new FileInputStream(realPath + File.separator + fileName);

        String fileType = this.getServletContext().getMimeType(fileName);
        response.setHeader("content-type", fileType);

        response.setHeader("content-disposition", "attachment;fileName=" + fileDC.getTitle());

        ServletOutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024 * 8];
        int len = 0;
        while ((len = file.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        file.close();
        outputStream.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.doPost(request, response);
    }
}
