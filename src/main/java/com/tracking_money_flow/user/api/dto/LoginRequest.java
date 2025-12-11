package com.tracking_money_flow.user.api.dto;

public record LoginRequest(
        String email,
        String password
) {
}
