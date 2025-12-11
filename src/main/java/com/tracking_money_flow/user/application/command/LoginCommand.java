package com.tracking_money_flow.user.application.command;

public record LoginCommand(
        String email,
        String password
) {
}
