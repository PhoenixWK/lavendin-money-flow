package com.tracking_money_flow.role.domain.exception;

public class RoleAlreadyExistException extends RuntimeException{
    public RoleAlreadyExistException(String msg) {
        super(msg);
    }
}
