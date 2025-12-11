package com.tracking_money_flow.user.application.usecase;

import com.tracking_money_flow.user.application.command.RegisterUserCommand;
import com.tracking_money_flow.user.application.port.PasswordHasher;
import com.tracking_money_flow.user.domain.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


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

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateOfBirth dateOfBirth;

        try {
            Date dobDate = formatter.parse(cmd.dateOfBirth());
            dateOfBirth = new DateOfBirth(dobDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date of birth format. Expected format: yyyy-MM-dd");
        }

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User user = User.register(email, hashed, userName, dateOfBirth, createdAt, updatedAt);

        userRepository.save(user);

    }

}
