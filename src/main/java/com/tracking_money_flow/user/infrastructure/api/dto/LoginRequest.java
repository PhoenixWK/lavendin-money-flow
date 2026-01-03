package com.tracking_money_flow.user.infrastructure.api.dto;

public record LoginRequest(
        String email,
        String password
) {
}
