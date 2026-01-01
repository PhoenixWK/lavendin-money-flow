package com.tracking_money_flow.user.infrastructure.persistence.jpa;

import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.UserId;
import com.tracking_money_flow.user.application.port.UserRepository;
import com.tracking_money_flow.user.infrastructure.persistence.mapper.UserMapper;


import java.util.Optional;

public class JpaUserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public JpaUserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void save(User user) {
        UserJpaEntity entity = UserMapper.toEntity(user);

        if (user.isNew()) {
            // For new users, use persist to let JPA generate the ID
            userJpaRepository.save(entity);
        } else {
            // For existing users, set the ID and use merge
            entity.setId(user.getId().value());
            userJpaRepository.save(entity);
        }
    }

    @Override
    public User saveUserWithReturnValue(User newUser) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return userJpaRepository.findByEmail(email.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(Email email) {
        return userJpaRepository.existsByEmail(email.value());
    }
}
