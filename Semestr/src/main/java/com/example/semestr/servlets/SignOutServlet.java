package com.example.semestr.servlets;

import com.example.semestr.services.SecurityService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign_out")
public class SignOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityService.signOut(request);
        response.sendRedirect(getServletContext().getContextPath() + "/main");
    }

}
