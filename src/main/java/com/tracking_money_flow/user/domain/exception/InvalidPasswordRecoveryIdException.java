package com.tracking_money_flow.user.domain.exception;

public class InvalidPasswordRecoveryIdException extends RuntimeException {
    public InvalidPasswordRecoveryIdException(String message) {
        super(message);
    }
}
