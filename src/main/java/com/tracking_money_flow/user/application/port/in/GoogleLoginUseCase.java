package com.tracking_money_flow.user.application.port.in;

import com.tracking_money_flow.user.application.command.GoogleLoginCommand;
import com.tracking_money_flow.user.application.port.out.TokenProvider;
import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;

import java.util.Optional;

public class GoogleLoginUseCase {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public GoogleLoginUseCase(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public String execute(GoogleLoginCommand cmd) {
        Email email = new Email(cmd.email());

        // Check if user exists
        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;
        // User exists, use it
        // Create new user with Google provider
        // Save the new user
        user = existingUser.orElseGet(() -> userRepository.saveUserWithReturnValue(User.registerWithGoogle(email)));

        // Generate and return token
        return tokenProvider.generateToken(user);
    }
}

