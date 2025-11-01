package com.xeno.crm_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://crm-frontend-three-wheat.vercel.app", "http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        try {
            System.out.println("UserController - /api/user called");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("UserController - Authentication: " + (authentication != null ? authentication.getName() : "null"));
            
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication.getPrincipal().equals("anonymousUser")) {
                System.out.println("UserController - No authentication found or anonymous user");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not authenticated"));
            }

            Map<String, Object> userInfo = new HashMap<>();
            
            // Check if JWT claims are available (JWT auth)
            Claims claims = (Claims) request.getAttribute("userClaims");
            
            if (claims != null) {
                // JWT authentication
                System.out.println("UserController - Authenticated via JWT: " + claims.get("email"));
                userInfo.put("email", claims.get("email"));
                userInfo.put("name", claims.get("name"));
                userInfo.put("picture", claims.get("picture"));
            } else {
                // Try OAuth2 session authentication (fallback)
                try {
                    OAuth2User user = (OAuth2User) authentication.getPrincipal();
                    System.out.println("UserController - Authenticated via OAuth2 session: " + user.getAttribute("email"));
                    userInfo.put("email", user.getAttribute("email"));
                    userInfo.put("name", user.getAttribute("name"));
                    userInfo.put("picture", user.getAttribute("picture"));
                } catch (ClassCastException e) {
                    // Not OAuth2User, try to get from authentication name
                    System.out.println("UserController - Using authentication name as fallback");
                    userInfo.put("email", authentication.getName());
                    userInfo.put("name", authentication.getName());
                    userInfo.put("picture", "");
                }
            }
            
            System.out.println("UserController - Returning user info: " + userInfo);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            System.err.println("UserController - Error in getUser: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error", "message", e.getMessage()));
        }
    }
}
