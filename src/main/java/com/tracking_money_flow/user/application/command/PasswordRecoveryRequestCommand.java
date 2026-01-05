package com.tracking_money_flow.user.application.command;

public record PasswordRecoveryRequestCommand(
    String email
) {
}
