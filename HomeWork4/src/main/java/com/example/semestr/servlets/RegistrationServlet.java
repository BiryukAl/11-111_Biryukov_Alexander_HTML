package com.example.semestr.servlets;

import com.example.semestr.entities.User;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.RegistrationServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login.isEmpty() || name.isEmpty() || password.isEmpty()) {
            User user = User.builder().name(name).login(login).password(password).build();

            CRUDRepositoryUserImpl repositoryUser = (CRUDRepositoryUserImpl) req.getAttribute("datasource");

            repositoryUser.save(user);

            if (RegistrationServices.signIn(req, name, login, password)) {
//                resp.sendRedirect(getServletContext().getContextPath() + "/profile");


                req.setAttribute("message", "Done");
                return;
            }
            req.setAttribute("login", req.getParameter("login"));

        }else {
            req.setAttribute("message", "You have to fill all form fields.");
        }

    }
}
