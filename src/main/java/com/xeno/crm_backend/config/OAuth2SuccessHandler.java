package com.xeno.crm_backend.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.xeno.crm_backend.util.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        
        // Generate JWT token
        String token = jwtUtil.generateToken(email, name, picture);
        
        // Log for debugging
        System.out.println("OAuth2 Success - User: " + email);
        System.out.println("Generated JWT token");
        
        // Redirect to frontend with token as query parameter
        String redirectUrl = frontendUrl + "/home?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
        System.out.println("Redirecting to: " + redirectUrl);
        
        response.sendRedirect(redirectUrl);
    }
}
