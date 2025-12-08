package com.tracking_money_flow.user.domain;

public class UserName {
    private String username;

    public UserName(String username) {
        if(username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        this.username = username;
    }

    public String value() {
        return username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
