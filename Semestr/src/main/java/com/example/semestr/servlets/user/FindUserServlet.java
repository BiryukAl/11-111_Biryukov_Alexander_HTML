package com.example.semestr.servlets.user;

import com.example.semestr.entities.User;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/user/find")
public class FindUserServlet extends HttpServlet {
    private CRUDRepositoryUserImpl repositoryUser;

    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/users/find_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUser = Long.valueOf(request.getParameter("idUser"));

        User user =  repositoryUser.findById(idUser);

        if (user == null){
            request.setAttribute("message", "Not User");
            getServletContext().getRequestDispatcher("/WEB-INF/views/users/find_user.jsp").forward(request, response);
        }else {
            response.sendRedirect(getServletContext().getContextPath() + "/user?idUser="+idUser);
        }
    }
}
