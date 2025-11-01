package com.xeno.crm_backend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xeno.crm_backend.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        System.out.println("JWT Filter - Request: " + request.getMethod() + " " + request.getRequestURI());
        
        String authHeader = request.getHeader("Authorization");
        System.out.println("JWT Filter - Authorization header: " + (authHeader != null ? "Present" : "Not present"));
        
        String token = null;
        String email = null;

        // Extract token from Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            System.out.println("JWT Filter - Token extracted, length: " + token.length());
            try {
                email = jwtUtil.extractEmail(token);
                System.out.println("JWT Filter - Email extracted: " + email);
            } catch (Exception e) {
                System.err.println("JWT Filter - Error extracting email from token: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Validate token and set authentication
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)) {
                    Claims claims = jwtUtil.extractClaims(token);
                    
                    UserDetails userDetails = User.builder()
                            .username(email)
                            .password("")
                            .authorities("USER")
                            .build();

                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Store claims in request attribute for controllers to access
                    request.setAttribute("userClaims", claims);
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("JWT Filter - Authentication successful for: " + email);
                } else {
                    System.out.println("JWT Filter - Token validation failed or expired");
                }
            } catch (Exception e) {
                System.err.println("JWT Filter - Error during token validation: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (email == null) {
            System.out.println("JWT Filter - No email extracted from token");
        } else {
            System.out.println("JWT Filter - Authentication already exists");
        }

        filterChain.doFilter(request, response);
    }
}
