package com.tracking_money_flow.user.domain;

public class Password {
    private final String password;

    private Password(String password) {
        this.password = password;
    }

    //using before hashing (convert String to Password)
    public static Password raw(String password) {
        if (password == null || password.length() < 10) {
            throw new IllegalArgumentException("Password must be at least 10 characters long");
        }
        return new Password(password);
    }

    //convert hashed String to Password
    public static Password hashed(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            throw new IllegalArgumentException("Hashed password cannot be null or empty");
        }
        return new Password(hashedPassword);
    }

    public String value() {
        return password;
    }
}
