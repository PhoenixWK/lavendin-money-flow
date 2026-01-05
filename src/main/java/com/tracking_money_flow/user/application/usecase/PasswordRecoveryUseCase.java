package com.tracking_money_flow.user.application.usecase;

import com.tracking_money_flow.user.application.command.PasswordRecoveryCommand;
import com.tracking_money_flow.user.application.port.PasswordHasher;
import com.tracking_money_flow.user.application.port.PasswordRecoveryRepository;
import com.tracking_money_flow.user.application.port.UserRepository;
import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.Password;
import com.tracking_money_flow.user.domain.PasswordRecovery;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.exception.ExpiredCodeException;
import com.tracking_money_flow.user.domain.exception.InvalidPasswordRecoveryIdException;
import com.tracking_money_flow.user.domain.exception.NoUserFoundException;

public class PasswordRecoveryUseCase {
    private final PasswordHasher passwordHasher;
    private final UserRepository userRepository;
    private final PasswordRecoveryRepository passwordRecoveryRepository;

    public PasswordRecoveryUseCase(PasswordHasher passwordHasher, UserRepository userRepository, PasswordRecoveryRepository passwordRecoveryRepository) {
        this.passwordHasher = passwordHasher;
        this.userRepository = userRepository;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
    }

    public void execute(PasswordRecoveryCommand cmd) {
        PasswordRecovery passwordRecovery = passwordRecoveryRepository.findById(cmd.attachedId())
                .orElseThrow(() -> new InvalidPasswordRecoveryIdException("Invalid password recovery code"));

        boolean status = passwordRecovery.getIsExpired();

        if(status) {
            throw new ExpiredCodeException("The password recovery code is expired!");
        }

        User user = userRepository.findByEmail(new Email(passwordRecovery.getRequestBy()))
                .orElseThrow(() -> new NoUserFoundException("No user found!"));

        String hashedPassword = passwordHasher.hash(cmd.newPassword());

        user.changePassword(Password.hashed(hashedPassword));

        userRepository.save(user);

        passwordRecoveryRepository.updateExpiredStatus(cmd.attachedId());
    }
}
