package com.tracking_money_flow.user.application.port.in;

import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.exception.NoUserFoundException;

public class GetUserWithEmailUseCase {
    private final UserRepository userRepository;

    public GetUserWithEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new NoUserFoundException("No user found with the provided email"));
    }
}
