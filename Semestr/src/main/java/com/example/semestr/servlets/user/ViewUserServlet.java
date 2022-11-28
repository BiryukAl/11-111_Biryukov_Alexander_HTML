package com.example.semestr.servlets.user;

import com.example.semestr.entities.FileDC;
import com.example.semestr.entities.User;
import com.example.semestr.repositories.CRUDRepositoryFileAccessImpl;
import com.example.semestr.repositories.CRUDRepositoryFileImpl;
import com.example.semestr.repositories.CRUDRepositoryFriendsImpl;
import com.example.semestr.repositories.CRUDRepositoryUserImpl;
import com.example.semestr.services.DecorationPages;
import com.example.semestr.services.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class ViewUserServlet extends HttpServlet {

    private CRUDRepositoryUserImpl repositoryUser;
    private CRUDRepositoryFriendsImpl repositoryFriends;
    private CRUDRepositoryFileImpl repositoryFile;
    private CRUDRepositoryFileAccessImpl repositoryFileAccess;



    @Override
    public void init() throws ServletException {
        repositoryUser = (CRUDRepositoryUserImpl) getServletContext().getAttribute("repositoryUser");
        repositoryFriends = (CRUDRepositoryFriendsImpl) getServletContext().getAttribute("repositoryFriends");
        repositoryFile = (CRUDRepositoryFileImpl) getServletContext().getAttribute("repositoryFile");
        repositoryFileAccess = (CRUDRepositoryFileAccessImpl) getServletContext().getAttribute("repositoryFileAccess");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUser = Long.valueOf(request.getParameter("idUser"));


        if (SecurityService.isSigned(request)) {
            Long myId = (Long) request.getSession().getAttribute("user_id");

            if (idUser.equals(myId)){
                response.sendRedirect(getServletContext().getContextPath() + "/profile");
                return;
            }
        }

        User user =  repositoryUser.findById(idUser);

        DecorationPages.setTitle(request, user.getName());

        request.setAttribute("friend_name", user.getName());
        request.setAttribute("friend_id", user.getId());
        request.setAttribute("subscribers", repositoryFriends.countSubscribers(idUser));

        if (SecurityService.isSigned(request)){
            boolean isFriend = repositoryFriends.isFriend((Long) request.getSession().getAttribute("user_id"), idUser) != null ;
            request.setAttribute("is_friend", isFriend);
        }


        // TODO: 27.11.2022 With UserAccess
        List<FileDC> filesFriend = repositoryFile.findPublicByIdUser(idUser);

        request.setAttribute("items_friend_files", filesFriend);

        getServletContext().getRequestDispatcher("/WEB-INF/views/users/user.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
