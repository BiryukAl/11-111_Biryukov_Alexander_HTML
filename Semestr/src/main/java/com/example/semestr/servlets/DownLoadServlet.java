package com.example.semestr.servlets;

import com.example.semestr.utils.DownLoadUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;


public class DownLoadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Получить параметры запроса, имя файла
        String fileName = request.getParameter("fileName");

        // 2. Использование байтового входного потока для загрузки файлов в память

        //2.1 найти путь к файловому серверу
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/img/" + fileName);

        //2.2 Использовать поток байтов для чтения файла в память
        FileInputStream file = new FileInputStream(realPath);

        // 3. Устанавливаем заголовок ответа

        //3.1 Установите тип заголовка ответа: content-type
        // Получаем MIME-тип файла
        String fileType = servletContext.getMimeType(fileName);
        response.setHeader("content-type", fileType);

        //3.2 Установить метод открытия заголовка ответа: content-disposition

        // Решаем проблему китайского имени файла

        // I. Получить заголовок запроса пользовательского агента
        String agent = request.getHeader("user-agent");
        // II. Используйте метод инструмента для кодирования имени файла
        fileName = DownLoadUtils.getFileName(agent, fileName);

        response.setHeader("content-disposition", "attachment;fileName" + fileName);

        // 4. Записываем данные из памяти в выходной поток
        ServletOutputStream sos = response.getOutputStream();
        // Устанавливаем буферную область
        byte[] buffer = new byte[1024 * 8];
        int len = 0;
        while ((len = file.read(buffer)) != -1) {
            sos.write(buffer, 0, len);
        }

        // Закрываем поток
        file.close();
        sos.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
