package com.example.semestr.servlets.account;

import com.example.semestr.entities.User;
import com.example.semestr.exceptions.NoFoundRows;
import com.example.semestr.exceptions.NotUniqueLogin;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.DecorationPages;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile/edit")
public class EditProfileServlet extends HttpServlet {

    private CRUDRepositoryUserImpl repositoryUser;

    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request, "Edit: " + (String) request.getSession().getAttribute("user_name"));
        if (!SecurityService.isSigned(request)) {
            response.sendRedirect(getServletContext().getContextPath() + "/main");
            return;
        }

        Long idUser = (Long) request.getSession().getAttribute("user_id");
        User user = repositoryUser.findById(idUser);

        request.setAttribute("name", user.getName());
        request.setAttribute("login", user.getLogin());
        getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_profile.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String oldPassword = request.getParameter("old_password");

        if (!(name.isEmpty() || login.isEmpty())) {
            String idLogin = (String) request.getSession().getAttribute("user_login");
            User oldUser = repositoryUser.findByLoginAndPassword(idLogin, oldPassword);

            if (oldUser == null) {
                request.setAttribute("message", "не правильный пароль");
                request.setAttribute("name", name);
                request.setAttribute("login", login);
                getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_profile.jsp").forward(request, response);
                return;
            }

            User newUser = User.builder()
                    .id(oldUser.getId())
                    .name(name)
                    .login(login)
                    .password(oldUser.getPassword())
                    .build();

            try {
                repositoryUser.update(newUser);
            } catch (NoFoundRows e) {
                request.setAttribute("message", "Error");
                getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_profile.jsp").forward(request, response);
            } catch (NotUniqueLogin e) {
                request.setAttribute("message", "Not Unique Login");
                getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_profile.jsp").forward(request, response);
            }
            SecurityService.signOut(request);
            SecurityService.signIn(request, newUser);


            response.sendRedirect(getServletContext().getContextPath() + "/profile");
            return;

        } else {
            request.setAttribute("message", "You have to fill all form fields.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/account/edit_profile.jsp").forward(request, response);
        }


    }
}
