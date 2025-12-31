package com.tracking_money_flow.user.domain;

import java.util.regex.Pattern;

public class Password {
    private final String password;

    private Password(String password) {
        this.password = password;
    }

    //using before hashing (convert String to Password)
    public static Password raw(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[!@#$%&*_])[A-Za-z\\\\d!@#$%&*_]{10,}$";
        Pattern pattern = Pattern.compile(regex);

        if (password == null || (password.length() < 10 && !pattern.matcher(password).matches())) {
            throw new IllegalArgumentException("Password is not valid");
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
