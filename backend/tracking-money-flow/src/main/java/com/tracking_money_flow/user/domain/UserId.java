package com.tracking_money_flow.user.domain;

public class UserId {
    private final String id;

    private UserId(String id) {
        this.id = id;
    }

    public static String generate() {
        return java.util.UUID.randomUUID().toString();
    }

    public static UserId create(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
        return new UserId(id);
    }

    public String value() {
        return id;
    }
}
