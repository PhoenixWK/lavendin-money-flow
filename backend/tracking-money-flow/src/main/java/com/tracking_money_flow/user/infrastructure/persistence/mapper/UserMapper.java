package com.tracking_money_flow.user.infrastructure.persistence.mapper;

import com.tracking_money_flow.user.domain.*;

import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserJpaEntity;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserStatus;

public class UserMapper {
    public static UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(
                user.getId().toString(),
                user.getEmail().toString(),
                user.getUsername().toString(),
                user.getPassword().toString(),
                user.getDateOfBirth().value(),
                user.getStatus().toString().equals("INACTIVE") ? UserStatus.INACTIVE :
                user.getStatus().toString().equals("LOCKED") ? UserStatus.LOCKED : UserStatus.ACTIVE,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static User toDomain(UserJpaEntity entity) {
        return User.register(
                new Email(entity.getEmail()),
                Password.hashed(entity.getPassword()),
                new UserName(entity.getUsername()),
                new DateOfBirth(entity.getDateOfBirth()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
