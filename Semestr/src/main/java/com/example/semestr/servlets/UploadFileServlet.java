package com.example.semestr.servlets;

import com.example.semestr.entities.FileDC;
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

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet("/upload")
public class UploadFileServlet extends HttpServlet {

    // TODO: 22.11.2022 Добавить путь до загрузки

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/upload_file.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean publicAccess = request.getParameter("public_access") != null; //== null ? false : true

        Part filePart = request.getPart("file");


        String uploadPath = (String) getServletContext().getAttribute("uploadPath");

        for (Part part : request.getParts()) {
//            String fileName = RandomFilePath.generateFileName(part.getName());
            String fileName = RandomFilePath.getFileName(part);
            FileDC fileDC = FileDC.builder().title(title).description(description).holderId((Long) request.getSession().getAttribute("user_id")).nameFile(fileName).publicAccess(publicAccess).build();
            // TODO: 22.11.2022 Add DB
//            repositoryFileDC.save(fileDC);
            part.write(uploadPath + File.separator + fileName);
        }





    }
}


