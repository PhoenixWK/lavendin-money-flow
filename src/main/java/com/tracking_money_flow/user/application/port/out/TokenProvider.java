package com.tracking_money_flow.user.application.port.out;

import com.tracking_money_flow.user.domain.User;
import io.jsonwebtoken.Claims;

public interface TokenProvider {
    String generateToken(User user);
    String extractEmail(String token);
    boolean isTokenValid(String token);
    Claims extractClaims(String token);
}

