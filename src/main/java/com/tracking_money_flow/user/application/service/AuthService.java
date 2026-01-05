package com.tracking_money_flow.user.application.service;

import com.tracking_money_flow.user.application.command.*;
import com.tracking_money_flow.user.application.usecase.*;
import org.springframework.transaction.annotation.Transactional;


public class AuthService {
    private LoginUserUseCase loginUserUseCase;
    private RegisterUserUseCase registerUserUseCase;
    private PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase;
    private GoogleLoginUseCase googleLoginUseCase;
    private PasswordRecoveryUseCase passwordRecoveryUseCase;

    public AuthService(
        LoginUserUseCase loginUserUseCase,
        RegisterUserUseCase registerUserUseCase,
        PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase,
        GoogleLoginUseCase googleLoginUseCase,
        PasswordRecoveryUseCase passwordRecoveryUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.passwordRecoveryRequestUseCase = passwordRecoveryRequestUseCase;
        this.googleLoginUseCase = googleLoginUseCase;
        this.passwordRecoveryUseCase = passwordRecoveryUseCase;
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

    @Transactional
    public void passwordRecoveryRequest(PasswordRecoveryRequestCommand cmd) {
        passwordRecoveryRequestUseCase.execute(cmd);
    }

    public void passwordRecovery(PasswordRecoveryCommand cmd) {
        passwordRecoveryUseCase.execute(cmd);
    }
}
