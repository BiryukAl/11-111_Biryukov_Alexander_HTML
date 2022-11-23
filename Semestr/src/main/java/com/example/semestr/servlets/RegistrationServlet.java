package com.example.semestr.servlets;

import com.example.semestr.entities.User;
import com.example.semestr.exeption.DbException;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Security;


@WebServlet(value = "/register")
public class RegistrationServlet extends HttpServlet {

    private CRUDRepositoryUserImpl repositoryUser;

    @Override
    public void init() throws ServletException {

        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("datasource");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title_page", "My Site");
        request.setAttribute("title_header", "Account");
        getServletContext().getRequestDispatcher("/WEB-INF/views/sing_up.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        request.setAttribute("name", name);
        if ( !(name.isEmpty() || login.isEmpty() || password.isEmpty())) {
            User user = User.builder().name(name).login(login).password(password).build();

            try {
                repositoryUser.save(user);
            } catch (DbException e) {
                request.setAttribute("message", "User with such login already exists.");
                getServletContext().getRequestDispatcher("/WEB-INF/views/sing_up.jsp").forward(request, response);
                return;
            }

            SecurityService.signIn(request, user);
            response.sendRedirect(getServletContext().getContextPath() + "/profile");

            request.setAttribute("name", request.getParameter("name"));

        }else {
            request.setAttribute("message", "You have to fill all form fields.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/sing_up.jsp").forward(request, response);
        }
    }
}
