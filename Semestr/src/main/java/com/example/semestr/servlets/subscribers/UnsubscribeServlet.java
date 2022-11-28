package com.example.semestr.servlets.subscribers;

import com.example.semestr.exceptions.NoFoundRows;
import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/subscriptions/unsubscribe")
public class UnsubscribeServlet extends HttpServlet {

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
            try {
                repositoryFriends.delete(idUser, idFriend);
            } catch (NoFoundRows e) {
                System.out.println("POOP");
            }
        }

        response.sendRedirect(getServletContext().getContextPath() + "/subscriptions");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
