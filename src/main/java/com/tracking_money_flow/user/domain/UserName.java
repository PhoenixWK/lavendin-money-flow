package com.tracking_money_flow.user.domain;

import java.util.regex.Pattern;

public class UserName {
    private String username;

    /*
        Username can contains lowercase, uppercase letters and digits only
    */
    public UserName(String username) {
        String regex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);

        if(username == null || username.isBlank() || !pattern.matcher(username).matches()) {
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
