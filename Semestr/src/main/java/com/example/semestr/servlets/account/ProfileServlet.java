package com.example.semestr.servlets.account;

import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import com.example.semestr.services.DecorationPages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private CRUDRepositoryFriendsImpl repositoryFriends;

    @Override
    public void init() throws ServletException {
        repositoryFriends = (CRUDRepositoryFriendsImpl) getServletContext().getAttribute("repositoryFriends");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DecorationPages.setTitle(request, (String) request.getSession().getAttribute("user_name"));
        request.setAttribute(
                "subscribers",
                repositoryFriends.countSubscribers(
                        (Long) request
                                .getSession()
                                .getAttribute("user_id"))
        );
        getServletContext().getRequestDispatcher("/WEB-INF/views/account/profile.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
