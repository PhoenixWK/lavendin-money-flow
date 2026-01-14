package com.tracking_money_flow.user.infrastructure.security;

import com.tracking_money_flow.user.application.port.out.TokenProvider;
import com.tracking_money_flow.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

public class JwtTokenProvider implements TokenProvider {

    private static final String SECRET_KEY = "mySecretKeyForJWTTokenGenerationThatIsLongEnoughForHS256Algorithm";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hours in milliseconds

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String subject = user.getId() != null ? user.getId().value() : user.getEmail().value();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        try {
            return extractClaims(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract email from token", e);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            boolean expired = isTokenExpired(token);
            return !expired;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}

