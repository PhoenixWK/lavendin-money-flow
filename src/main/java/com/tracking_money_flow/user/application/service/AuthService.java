package com.tracking_money_flow.user.application.service;

import com.tracking_money_flow.user.application.command.LoginCommand;
import com.tracking_money_flow.user.application.command.RegisterUserCommand;
import com.tracking_money_flow.user.application.usecase.LoginUserUseCase;
import com.tracking_money_flow.user.application.usecase.RegisterUserUseCase;


public class AuthService {
    private LoginUserUseCase loginUserUseCase;
    private RegisterUserUseCase registerUserUseCase;

    public AuthService(
        LoginUserUseCase loginUserUseCase,
        RegisterUserUseCase registerUserUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    public String login(LoginCommand cmd) {
        return loginUserUseCase.execute(cmd);
    }

    public void register(RegisterUserCommand cmd) {
        registerUserUseCase.execute(cmd);
    }



}
