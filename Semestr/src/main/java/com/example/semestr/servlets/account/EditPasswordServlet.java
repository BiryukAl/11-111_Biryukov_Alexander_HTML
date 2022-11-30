package com.example.semestr.servlets.account;

import com.example.semestr.entities.User;
import com.example.semestr.exceptions.NoFoundRows;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.DecorationPages;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile/edit/password")
public class EditPasswordServlet extends HttpServlet {

    private CRUDRepositoryUserImpl repositoryUser;


    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request, "Edit: " + (String) request.getSession().getAttribute("user_name"));
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
