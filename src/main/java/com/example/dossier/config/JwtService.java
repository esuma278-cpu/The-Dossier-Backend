package com.example.dossier.config;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.dossier.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final long TOKEN_LIFETIME_HOURS = 8;

    private final SecretKey signingKey;

    public JwtService(@Value("${JWT_SECRET:}") String secret) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException(
                    "JWT_SECRET must contain at least 32 bytes."
            );
        }

        this.signingKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user, String role) {
        Instant issuedAt = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", role)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(
                        issuedAt.plus(TOKEN_LIFETIME_HOURS, ChronoUnit.HOURS)
                ))
                .signWith(signingKey)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
