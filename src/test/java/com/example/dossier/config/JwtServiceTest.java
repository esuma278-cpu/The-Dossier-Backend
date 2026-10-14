package com.example.dossier.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.dossier.entity.User;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    private static final String SECRET =
            "test-secret-that-is-long-enough-for-hmac-sha256";

    @Test
    void generatedTokenIdentifiesUserAndRole() {
        JwtService jwtService = new JwtService(SECRET);
        User investigator = new User();
        investigator.setUsername("investigator");

        String token = jwtService.generateToken(investigator, "INVESTIGATOR");

        assertEquals(
                "investigator",
                jwtService.parseClaims(token).getSubject()
        );
        assertEquals(
                "INVESTIGATOR",
                jwtService.parseClaims(token).get("role", String.class)
        );
    }

    @Test
    void rejectsSecretsShorterThanThirtyTwoBytes() {
        assertThrows(
                IllegalStateException.class,
                () -> new JwtService("short")
        );
    }

    @Test
    void rejectsTokensSignedByAnotherSecret() {
        JwtService issuer = new JwtService(SECRET);
        JwtService verifier =
                new JwtService("another-secret-with-at-least-32-bytes");
        User investigator = new User();
        investigator.setUsername("investigator");

        String token = issuer.generateToken(investigator, "INVESTIGATOR");

        assertThrows(JwtException.class, () -> verifier.parseClaims(token));
    }
}
