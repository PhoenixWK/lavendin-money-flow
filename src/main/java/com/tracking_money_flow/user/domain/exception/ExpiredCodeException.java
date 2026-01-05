package com.tracking_money_flow.user.domain.exception;

public class ExpiredCodeException extends RuntimeException {
    public ExpiredCodeException(String message) {
        super(message);
    }
}
