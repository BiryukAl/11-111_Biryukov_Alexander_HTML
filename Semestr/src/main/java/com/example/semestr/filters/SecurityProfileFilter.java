package com.example.semestr.filters;

import com.example.semestr.services.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter("/profile/*")
public class SecurityProfileFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (SecurityService.isSigned(request)) {
            super.doFilter(request, response, chain);
        } else {
            response.sendRedirect(request.getContextPath() + "/account/sign_in");
        }
    }
}
