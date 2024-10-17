package com.nusiss.dmss.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtTokenService {

    // Secret key for signing the JWT. Keep this secure and don't expose it.
    public static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static  String generateToken(String username, String password) {
        // Get the current time
        LocalDateTime now = LocalDateTime.now();

        // Set expiration time to 30 minutes after the current time
        LocalDateTime expireDateTime = now.plusMinutes(30);

        // Convert expiration time to Date for JWT
        Date expirationDate = java.sql.Timestamp.valueOf(expireDateTime);

        // Convert expiration time to string for storing in JWT claims
        String expireDateTimeStr = expireDateTime.format(DATE_TIME_FORMATTER);

        // Add claims (custom fields like username, password, and expireDateTime)
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("password", password);

        // Build and sign the JWT token
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()) // Set issued time to now
                .setExpiration(expirationDate) // Set expiration to 30 minutes later
                .signWith(SECRET_KEY) // Sign with the secret key
                .compact();
    }

    /*// Set the JWT token in the cookie and add it to the response
    public void addTokenToCookie(HttpServletResponse response, String token) {
        Cookie authCookie = new Cookie("authCookie", token);
        authCookie.setHttpOnly(true); // To prevent JavaScript access to the cookie
        authCookie.setMaxAge(600); // 600 seconds = 10 minutes
        authCookie.setPath("/"); // Available for the entire application
        authCookie.setSecure(false);
        authCookie.setAttribute("SameSite", "Lax");
        response.addCookie(authCookie);
    }*/
}
