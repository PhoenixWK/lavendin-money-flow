package com.tracking_money_flow.user.application.command;

//collect data from API
public record RegisterUserCommand(
    String username,
    String email,
    String password,
    String dateOfBirth
) {

}
