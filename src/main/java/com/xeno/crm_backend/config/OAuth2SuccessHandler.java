package com.xeno.crm_backend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        // Ensure session is created
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", authentication);
        
        // Set JSESSIONID cookie with proper settings for cross-origin
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(30 * 60); // 30 minutes
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
        
        // Log for debugging
        System.out.println("OAuth2 Success - User: " + authentication.getName());
        System.out.println("Session ID: " + session.getId());
        System.out.println("Redirecting to: " + frontendUrl + "/home");
        
        // Redirect to frontend
        response.sendRedirect(frontendUrl + "/home");
    }
}
