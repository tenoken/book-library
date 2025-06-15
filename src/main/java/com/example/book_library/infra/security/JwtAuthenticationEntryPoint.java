package com.example.book_library.infra.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String body = writeBody(authException);

        response.getWriter().write(body);
    }

    private String writeBody(AuthenticationException authException) {
        // TODO: CHANGE TO RESTERRORMESSAGE CLASS
        String body = """
                {
                    "status": "Unauthorized",
                    "message": "%s"
                }
                """.formatted(authException.getMessage());
        return body;
    }
}
