package com.tracking_money_flow.user.application.service;

import com.tracking_money_flow.user.application.command.*;
import com.tracking_money_flow.user.application.port.in.*;
import com.tracking_money_flow.user.domain.User;


public class AuthService {
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase;
    private final GoogleLoginUseCase googleLoginUseCase;
    private final PasswordRecoveryUseCase passwordRecoveryUseCase;
    private final GetUserWithEmailUseCase getUserWithEmailUseCase;

    public AuthService(
        LoginUserUseCase loginUserUseCase,
        RegisterUserUseCase registerUserUseCase,
        PasswordRecoveryRequestUseCase passwordRecoveryRequestUseCase,
        GoogleLoginUseCase googleLoginUseCase,
        PasswordRecoveryUseCase passwordRecoveryUseCase,
        GetUserWithEmailUseCase getUserWithEmailUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.passwordRecoveryRequestUseCase = passwordRecoveryRequestUseCase;
        this.googleLoginUseCase = googleLoginUseCase;
        this.passwordRecoveryUseCase = passwordRecoveryUseCase;
        this.getUserWithEmailUseCase = getUserWithEmailUseCase;
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

    public void passwordRecoveryRequest(PasswordRecoveryRequestCommand cmd) {
        passwordRecoveryRequestUseCase.execute(cmd);
    }

    public void passwordRecovery(PasswordRecoveryCommand cmd) {
        passwordRecoveryUseCase.execute(cmd);
    }

    public User getUserWithEmail(String email) {
        return getUserWithEmailUseCase.execute(email);
    }
}
