package com.example.semestr.services;

import com.example.semestr.entities.User;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityService {


    public static boolean isSigned(HttpServletRequest request) {
        return request.getSession().getAttribute("user_login") != null;
    }

    public static void signIn(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user_id", user.getId());
        request.getSession().setAttribute("user_name", user.getName());
        request.getSession().setAttribute("user_login", user.getLogin());
    }

    public static void signOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user_id");
        request.getSession().removeAttribute("user_login");
        request.getSession().removeAttribute("user_name");

    }
}
