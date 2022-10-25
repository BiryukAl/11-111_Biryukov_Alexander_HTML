package ru.itis.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(value = "/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("status") != null) {
            if (req.getParameter("status").equals("1")) {
                req.setAttribute("message", "User has been created.");
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/singin.jsp").forward(req, resp);
    }






}
