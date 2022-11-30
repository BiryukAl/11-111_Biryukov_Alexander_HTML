package com.example.semestr.servlets.user;

import com.example.semestr.entities.FriendDC;
import com.example.semestr.entities.User;
import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/find")
public class FindUserServlet extends HttpServlet {
    private CRUDRepositoryUserImpl repositoryUser;
    private CRUDRepositoryFriendsImpl repositoryFriends;


    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
        repositoryFriends = (CRUDRepositoryFriendsImpl) getServletContext().getAttribute("repositoryFriends");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/users/find_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("idUser") != null){
            Long idUser = Long.valueOf(request.getParameter("idUser"));

            User user = repositoryUser.findById(idUser);

            if (user == null) {
                request.setAttribute("message", "Not Find User");
                getServletContext().getRequestDispatcher("/WEB-INF/views/users/find_user.jsp").forward(request, response);
            } else {
                response.sendRedirect(getServletContext().getContextPath() + "/user?idUser=" + idUser);
            }
            return;
        }else if (request.getParameter("find_name_user") != null){
            String nameUser = request.getParameter("find_name_user");

            nameUser = nameUser.toLowerCase();

            List<FriendDC> friends = repositoryFriends.findUserByName(nameUser);
            request.setAttribute("items_friends", friends);
            getServletContext().getRequestDispatcher("/WEB-INF/views/users/find_user.jsp").forward(request, response);
            return;
        }
    }
}
