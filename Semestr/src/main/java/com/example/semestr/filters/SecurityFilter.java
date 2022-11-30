package com.example.semestr.filters;

import com.example.semestr.services.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

    protected final String[] protectedPaths = {
            "/profile",
            "/files/my",
            "/subscriptions"
    };

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean protect = false;

        for (String path : protectedPaths) {
            if (path.equals(request.getRequestURI().substring(request.getContextPath().length()))) {
                protect = true;
                break;
            }
        }

        if (protect && !SecurityService.isSigned(request)) {
            response.sendRedirect(request.getContextPath() + "/account/sign_in");
        } else {
            super.doFilter(request, response, chain);
        }


    }
}
