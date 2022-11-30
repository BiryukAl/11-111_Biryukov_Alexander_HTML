package com.example.semestr.services;

import jakarta.servlet.http.HttpServletRequest;

public class DecorationPages {
    public static void setTitle(HttpServletRequest request, String title) {
        request.setAttribute("title_page", title);
    }
}
