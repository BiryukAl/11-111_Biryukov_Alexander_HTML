package com.example.semestr.servlets;

import com.example.semestr.entities.User;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

    private CRUDRepositoryUserImpl repositoryUser;

    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/sign/sing_in.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        request.setAttribute("login", login);

        if (!(login.isEmpty() || password.isEmpty())) {


            User user = repositoryUser.findByLoginAndPassword(login, password);

            if (user == null) {
                request.setAttribute("message", "не правильный логин и/или пароль");
                getServletContext().getRequestDispatcher("/WEB-INF/views/sign/sing_in.jsp").forward(request, response);
            } else {
                SecurityService.signIn(request, user);
                response.sendRedirect(getServletContext().getContextPath() + "/profile");
            }

        } else {
            request.setAttribute("message", "You have to fill all form fields.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/sign/sing_in.jsp").forward(request, response);

        }
    }
}
