package com.example.semestr.servlets;

import com.example.semestr.entities.FileDC;
import com.example.semestr.exeption.DbException;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.services.RandomFilePath;
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
import static com.example.semestr.MainContextListener.UPLOAD_DIRECTORY;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, //Порог размера, после которого файл будет записан на диск
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5, //Максимальный размер, разрешенный для multipart/form-data Запросы
        location = UPLOAD_DIRECTORY // Загружает вот сюда
)
@WebServlet("/upload")
public class UploadFileServlet extends HttpServlet {
    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/upload_file.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean publicAccess = request.getParameter("public_access") != null; //== null ? false : true

        Part filePart = request.getPart("file");

// TODO: 24.11.2022 Не работает в MainContextListener
        String uploadPath = FULL_UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }


//        String uploadPath = (String) getServletContext().getAttribute("uploadPath");

        String fileName = RandomFilePath.generateFileName(filePart.getSubmittedFileName());

        FileDC fileDC = FileDC.builder()
                .title(title)
                .description(description)
                .holderId((Long) request.getSession().getAttribute("user_id"))
                .nameFile(fileName)
                .publicAccess(publicAccess)
                .build();
        try {
            repositoryFile.save(fileDC);
        } catch (DbException e) {
            request.setAttribute("message", "POP)");
            getServletContext().getRequestDispatcher("/WEB-INF/views/page_file/upload_file.jsp").forward(request, response);
            return;
        }
        System.out.printf(uploadPath);
        System.out.printf(fileName);

        filePart.write(fileName);


        request.setAttribute("message", "GOOD!");
        response.sendRedirect(getServletContext().getContextPath() + "/myfiles");


    }
}
