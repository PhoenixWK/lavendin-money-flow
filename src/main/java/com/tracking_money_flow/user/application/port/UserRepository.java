package com.tracking_money_flow.user.application.port;

import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.UserId;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    User saveUserWithReturnValue(User newUser);

    Optional<User> findByEmail(Email email);

    Optional<User> findById(UserId id);

    boolean existsByEmail(Email email);


}
