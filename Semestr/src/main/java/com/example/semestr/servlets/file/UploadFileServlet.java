package com.example.semestr.servlets.file;

import com.example.semestr.entities.FileDC;
import com.example.semestr.exceptions.DbException;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.DecorationPages;
import com.example.semestr.services.RandomFilePath;
import com.example.semestr.utils.UserAccessUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import static com.example.semestr.MainContextListener.FULL_UPLOAD_DIRECTORY;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, //Порог размера, после которого файл будет записан на диск
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5, //Максимальный размер, разрешенный для multipart/form-data Запросы
        location = FULL_UPLOAD_DIRECTORY // Загружает вот сюда
)
@WebServlet("/file/upload")
public class UploadFileServlet extends HttpServlet {
    private CRUDRepositoryFileImpl repositoryFile;
    private CRUDRepositoryFileAccessImpl repositoryFileAccess;


    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
        repositoryFileAccess = (CRUDRepositoryFileAccessImpl) getServletContext().getAttribute("repositoryFileAccess");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request,"Upload Files");
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/upload_file.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean publicAccess = request.getParameter("public_access") != null; //== null ? false : true

        String userAccess = request.getParameter("user_access");

        Part filePart = request.getPart("file");

        Long userId = (Long) request.getSession().getAttribute("user_id");

// Не работает в MainContextListener
        String uploadPath = FULL_UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = RandomFilePath.generateFileName(filePart.getSubmittedFileName());

        FileDC fileDC = FileDC.builder()
                .title(title)
                .description(description)
                .holderId(userId)
                .nameFile(fileName)
                .publicAccess(publicAccess)
                .build();

        long[] accessUserIdLong = UserAccessUtil.convertToLong(userAccess);

        try {
            repositoryFile.save(fileDC);
            repositoryFileAccess.save(fileDC.getId(), userId);
            for (Long idUser : accessUserIdLong) {
                    repositoryFileAccess.save(fileDC.getId(), idUser);
            }

        } catch (DbException e) {
            request.setAttribute("message", "POP)");
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/upload_file.jsp").forward(request, response);
            return;
        }

        filePart.write(fileName);

        request.setAttribute("message", "GOOD!");
        response.sendRedirect(getServletContext().getContextPath() + "/files/my");


    }
}
