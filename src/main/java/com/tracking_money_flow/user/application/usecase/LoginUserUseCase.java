package com.tracking_money_flow.user.application.usecase;

import com.tracking_money_flow.user.application.command.LoginCommand;
import com.tracking_money_flow.user.application.port.PasswordHasher;
import com.tracking_money_flow.user.application.port.TokenProvider;
import com.tracking_money_flow.user.application.port.UserRepository;
import com.tracking_money_flow.user.domain.*;

public class LoginUserUseCase {
    private UserRepository userRepository;
    private PasswordHasher passwordHasher;
    private TokenProvider tokenProvider;

    public LoginUserUseCase(UserRepository userRepository, PasswordHasher passwordHasher, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenProvider = tokenProvider;
    }

    public String execute(LoginCommand cmd) {
        Email email = new Email(cmd.email());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        Password rawPassword = Password.raw(cmd.password());

        if(!passwordHasher.matches(rawPassword.value(), user.getPassword().value())) {
            throw new RuntimeException("Invalid email or password");
        }

        if(!(user.getStatus() == UserStatus.ACTIVE)) {
            throw new RuntimeException("User account is not active");
        }

        return tokenProvider.generateToken(user);
    }
}
