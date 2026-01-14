package com.tracking_money_flow.user.infrastructure.persistence.mapper;

import com.tracking_money_flow.user.domain.*;

import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserEntity;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.UserStatus;


public class UserMapper {


    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        // Don't set ID for new entities - let Hibernate generate it
        // Only set ID if it already exists in database
        if (!user.isNew()) {
            entity.setId(user.getId().value());
        }
        entity.setEmail(user.getEmail().value());
        entity.setUsername(user.getUsername().value());
        entity.setPassword(user.getPassword().value());
        entity.setDateOfBirth(user.getDateOfBirth().value());
        entity.setStatus(mapToJpaStatus(user.getStatus()));
        entity.setAuthProvider(user.getAuthProvider());

        // Only set timestamps for existing entities - new entities will use @PrePersist
        if (!user.isNew()) {
            entity.setCreatedAt(user.getCreatedAt());
            entity.setUpdatedAt(user.getUpdatedAt());
        }

        return entity;
    }

    private static UserStatus mapToJpaStatus(com.tracking_money_flow.user.domain.UserStatus domainStatus) {
        return switch (domainStatus.toString()) {
            case "INACTIVE" -> UserStatus.INACTIVE;
            case "LOCKED" -> UserStatus.LOCKED;
            default -> UserStatus.ACTIVE;
        };
    }

    public static User toDomain(UserEntity entity) {
        return User.reconstruct(
                UserId.create(entity.getId()),
                new Email(entity.getEmail()),
                Password.hashed(entity.getPassword()),
                new UserName(entity.getUsername()),
                entity.getAuthProvider(),
                new DateOfBirth(entity.getDateOfBirth().toString()),
                mapToDomainStatus(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private static com.tracking_money_flow.user.domain.UserStatus mapToDomainStatus(UserStatus jpaStatus) {
        return switch (jpaStatus) {
            case INACTIVE -> com.tracking_money_flow.user.domain.UserStatus.INACTIVE;
            case LOCKED -> com.tracking_money_flow.user.domain.UserStatus.LOCKED;
            default -> com.tracking_money_flow.user.domain.UserStatus.ACTIVE;
        };
    }
}
