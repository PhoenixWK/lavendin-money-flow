package com.tracking_money_flow.user.infrastructure.api.dto;

public record PasswordRecovery(
        String attachedId,
        String newPassword
) {
}
