package com.tracking_money_flow.user.domain;

public class Email {

    private final String value;

    public Email(String value) {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        return value.equals(((Email) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

