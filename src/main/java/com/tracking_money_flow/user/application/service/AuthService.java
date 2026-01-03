package com.tracking_money_flow.user.application.service;

import com.tracking_money_flow.user.application.command.GoogleLoginCommand;
import com.tracking_money_flow.user.application.command.LoginCommand;
import com.tracking_money_flow.user.application.command.PasswordRecoveryCommand;
import com.tracking_money_flow.user.application.command.RegisterUserCommand;
import com.tracking_money_flow.user.application.usecase.GoogleLoginUseCase;
import com.tracking_money_flow.user.application.usecase.LoginUserUseCase;
import com.tracking_money_flow.user.application.usecase.PasswordRecoveryUseCase;
import com.tracking_money_flow.user.application.usecase.RegisterUserUseCase;


public class AuthService {
    private LoginUserUseCase loginUserUseCase;
    private RegisterUserUseCase registerUserUseCase;
    private PasswordRecoveryUseCase passwordRecoveryUseCase;
    private GoogleLoginUseCase googleLoginUseCase;

    public AuthService(
        LoginUserUseCase loginUserUseCase,
        RegisterUserUseCase registerUserUseCase,
        PasswordRecoveryUseCase passwordRecoveryUseCase,
        GoogleLoginUseCase googleLoginUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.passwordRecoveryUseCase = passwordRecoveryUseCase;
        this.googleLoginUseCase = googleLoginUseCase;
    }

    public String login(LoginCommand cmd) {
        return loginUserUseCase.execute(cmd);
    }

    public void register(RegisterUserCommand cmd) {
        registerUserUseCase.execute(cmd);
    }

    public String loginWithGoogle(GoogleLoginCommand cmd) {
        return googleLoginUseCase.execute(cmd);
    }

    public void passwordRecovery(PasswordRecoveryCommand cmd) {
        passwordRecoveryUseCase.execute(cmd);
    }

}
