package com.tracking_money_flow.user.application.port.out;

public interface PasswordHasher {
    String hash(String raw);
    boolean matches(String raw, String hashed);
}
