package com.tracking_money_flow.user.api.dto;

public record RegisterUserRequest(
        String email,
        String username,
        String password,
        String dateOfBirth
) {
}
