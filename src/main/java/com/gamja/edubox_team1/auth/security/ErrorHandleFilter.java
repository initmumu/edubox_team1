package com.gamja.edubox_team1.auth.security;

import com.gamja.edubox_team1.auth.exception.ExpiredAccessTokenException;
import com.gamja.edubox_team1.common.exception.GlobalException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ErrorHandleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (GlobalException e) {
            handleException(response, e.getHttpStatus(), e.getCode(), e.getMessage());
        }
    }

    private void handleException(HttpServletResponse response, int status, int code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        String jsonResponse = String.format("""
                {
                    "meta": {
                        "code": %d,
                        "message": "%s"
                    }
                }
                """, code, message);

        response.getWriter().write(jsonResponse);
    }
}
