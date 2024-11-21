package com.example.hksalarycalculatorbackend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @Value("${jwt.expiration:3600000}") // 1 hour in milliseconds
    private Long JWT_EXPIRATION;

    // Initialize the key securely

    //add the secret key after the colon
    @Value("${jwt.secret:}")
    public void setJwtSecret(String secret) {
        // Decode the Base64 key or treat it as plain text
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 256 bits (32 characters long).");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // Securely generate the key
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS512) // Use secure key
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false; // Token is invalid or expired
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true; // Treat invalid tokens as expired
        }
    }
}
