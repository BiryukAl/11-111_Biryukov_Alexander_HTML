package com.example.semestr.servlets;

import com.example.semestr.entities.User;
import com.example.semestr.exeption.NoFoundRows;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/editpassword")
public class EditPasswordServlet extends HttpServlet {

    private CRUDRepositoryUserImpl repositoryUser;


    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SecurityService.isSigned(request)) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_password.jsp").forward(request, response);
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/main");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldPassword = request.getParameter("old_password");
        String newPassword = request.getParameter("new_password");

        if (!(oldPassword.isEmpty() || newPassword.isEmpty())) {
            User oldUser = repositoryUser.findByLoginAndPassword((String) request.getSession().getAttribute("user_login"), oldPassword);

            if (oldUser == null) {
                request.setAttribute("message", "не правильный пароль");
                getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_password.jsp").forward(request, response);
                return;
            }

            User newUser = User.builder()
                    .id(oldUser.getId())
                    .name(oldUser.getName())
                    .login(oldUser.getLogin())
                    .password(newPassword)
                    .build();

            try {
                repositoryUser.updatePassword(newUser);

            } catch (NoFoundRows e) {
                request.setAttribute("message", "не правильный пароль");
                getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_password.jsp").forward(request, response);
                return;
            }
            response.sendRedirect(getServletContext().getContextPath() + "/profile");

        }
    }
}
