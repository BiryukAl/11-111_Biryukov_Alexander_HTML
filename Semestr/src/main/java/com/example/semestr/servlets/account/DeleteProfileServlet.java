package com.example.semestr.servlets.account;

import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile/edit/delete/delete")
public class DeleteProfileServlet extends HttpServlet {
    private CRUDRepositoryUserImpl repositoryUser;

    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SecurityService.isSigned(request)) {
            Long userId = (Long) request.getSession().getAttribute("user_id");
            SecurityService.signOut(request);
            repositoryUser.delete(userId);
            response.sendRedirect(getServletContext().getContextPath() + "/main");
        } else {
            response.sendRedirect(getServletContext().getContextPath() + "/main");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
