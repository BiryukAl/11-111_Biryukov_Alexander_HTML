package com.example.semestr.servlets.subscribers;

import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/subscriptions/subscribe")
public class SubscribeServlet extends HttpServlet {

    private CRUDRepositoryFriendsImpl repositoryFriends;

    @Override
    public void init() throws ServletException {
        repositoryFriends = (CRUDRepositoryFriendsImpl) getServletContext().getAttribute("repositoryFriends");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUser = (Long) request.getSession().getAttribute("user_id");
        Long idFriend = Long.valueOf(request.getParameter("idUser"));

        if (!idUser.equals(idFriend)) {
            repositoryFriends.save(idUser, idFriend);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
