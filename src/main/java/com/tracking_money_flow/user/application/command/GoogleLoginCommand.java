package com.tracking_money_flow.user.application.command;

public record GoogleLoginCommand(
        String email,
        String name
) {
}
