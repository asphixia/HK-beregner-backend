package com.example.hksalarycalculatorbackend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import com.example.hksalarycalculatorbackend.model.Roles;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    @Value("${jwt.expiration:3600000}")
    private Long JWT_EXPIRATION;

    @Value("${jwt.secret:STjLg5q+33OD2JauZaKWifSKt6+5aKQvy8VrIWTfxOavl8Ha1SkDwFmesjaw2OsWC0zJJoNKmouZlU7QVF3fow==")
    public void setJwtSecret(String secret) {

        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 256 bits (32 characters long).");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, Roles role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS512)
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
            return false;
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
            return true;
        }
    }
}
