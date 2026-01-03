package com.tracking_money_flow.user.infrastructure.api.dto;

public record RegisterUserRequest(
        String email,
        String username,
        String password,
        String dateOfBirth
) {
}
