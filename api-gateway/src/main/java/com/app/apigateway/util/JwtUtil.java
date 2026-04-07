package com.app.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * Utility to validate JWT tokens at the gateway level.
 */
@Slf4j
@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            log.warn("Invalid JWT: {}", ex.getMessage());
        }
        return false;
    }

    // Extract email from token
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Extract roles from token
    public String getRolesFromToken(String token) {
        return parseClaims(token).get("roles", String.class);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
