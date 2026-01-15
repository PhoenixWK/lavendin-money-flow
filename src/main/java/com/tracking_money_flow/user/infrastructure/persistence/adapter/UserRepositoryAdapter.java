package com.tracking_money_flow.user.infrastructure.persistence.adapter;

import com.tracking_money_flow.user.domain.Email;
import com.tracking_money_flow.user.domain.User;
import com.tracking_money_flow.user.domain.UserId;
import com.tracking_money_flow.user.application.port.out.UserRepository;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserEntity;
import com.tracking_money_flow.user.infrastructure.persistence.repository.UserJpaRepository;
import com.tracking_money_flow.user.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void save(User user) {
        UserEntity entity = UserMapper.toEntity(user);

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
        UserEntity entity = UserMapper.toEntity(newUser);
        UserEntity savedEntity = userJpaRepository.save(entity);
        return UserMapper.toDomain(savedEntity);
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

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(UserMapper::toDomain);
    }
}
