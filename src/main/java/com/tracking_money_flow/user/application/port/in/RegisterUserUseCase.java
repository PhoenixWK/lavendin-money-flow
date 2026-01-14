package com.tracking_money_flow.user.application.port.in;

import com.tracking_money_flow.user.application.command.RegisterUserCommand;
import com.tracking_money_flow.user.application.port.out.PasswordHasher;
import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.domain.*;

import java.time.LocalDateTime;


public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;


    public RegisterUserUseCase(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public void execute(RegisterUserCommand cmd){
        Email email = new Email(cmd.email());

        if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        Password password = Password.raw(cmd.password());
        String hashedPassword = passwordHasher.hash(password.value());
        Password hashed = Password.hashed(hashedPassword);

        UserName userName = new UserName(cmd.username());

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User user = User.register(email, hashed, userName, new DateOfBirth(cmd.dateOfBirth()), AuthProvider.EMAIL_AND_PASSWORD, createdAt, updatedAt);

        userRepository.save(user);

    }

}
