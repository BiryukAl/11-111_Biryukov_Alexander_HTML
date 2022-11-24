package com.example.semestr.servlets;

import com.example.semestr.MainContextListener;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.utils.DownLoadUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/download")
public class DownLoadServlet extends HttpServlet {

    private CRUDRepositoryFileImpl repositoryFile;

    @Override
    public void init() throws ServletException {
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        https://russianblogs.com/article/9535893544/
        // 1. Получить параметры запроса, имя файла
        Long idFile = Long.valueOf(request.getParameter("idFile"));
        String fileName = repositoryFile.findById(idFile).getNameFile();
        // 2. Использование байтового входного потока для загрузки файлов в память
        //2.1 найти путь к файловому серверу
        String realPath = MainContextListener.FULL_UPLOAD_DIRECTORY;
        //2.2 Использовать поток байтов для чтения файла в память
        FileInputStream file = new FileInputStream(realPath + File.separator + fileName);
        // 3. Устанавливаем заголовок ответа
        //3.1 Установите тип заголовка ответа: content-type
        // Получаем MIME-тип файла
        String fileType = this.getServletContext().getMimeType(fileName);
        response.setHeader("content-type", fileType);
        //3.2 Установить метод открытия заголовка ответа: content-disposition;
        
        /*// Решаем проблему китайского имени файла
        // I. Получить заголовок запроса пользовательского агента
        String agent = request.getHeader("user-agent");
        // II. Используйте метод инструмента для кодирования имени файла
        fileName = DownLoadUtils.getFileName(agent, fileName);*/
        // TODO: 24.11.2022 Задать имя файла
        response.setHeader("content-disposition", "attachment;fileName" + fileName);
//        response.setHeader("content-disposition", "attachment;fileName" + repositoryFile.findById(idFile).getTitle());
        // 4. Записываем данные из памяти в выходной поток
        ServletOutputStream outputStream = response.getOutputStream();
        // Устанавливаем буферную область
        byte[] buffer = new byte[1024 * 8];
        int len = 0;
        while ((len = file.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        // Закрываем поток
        file.close();
        outputStream.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
