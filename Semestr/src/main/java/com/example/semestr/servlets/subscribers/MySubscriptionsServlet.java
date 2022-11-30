package com.example.semestr.servlets.subscribers;

import com.example.semestr.entities.FriendDC;
import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import com.example.semestr.services.DecorationPages;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/subscriptions")
public class MySubscriptionsServlet extends HttpServlet {
    private CRUDRepositoryFriendsImpl repositoryFriends;

    @Override
    public void init() throws ServletException {
        repositoryFriends = (CRUDRepositoryFriendsImpl) getServletContext().getAttribute("repositoryFriends");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUser = (Long) request.getSession().getAttribute("user_id");
        Long countSubscriptions = repositoryFriends.countFriends(idUser);
        request.setAttribute("count_subscriptions", countSubscriptions);

        DecorationPages.setTitle(request, "Subscriptions");

        List<FriendDC> friends = repositoryFriends.findFriendsByIdUser(idUser);

        request.setAttribute("items_friends", friends);

        getServletContext().getRequestDispatcher("/WEB-INF/views/users/friends.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
