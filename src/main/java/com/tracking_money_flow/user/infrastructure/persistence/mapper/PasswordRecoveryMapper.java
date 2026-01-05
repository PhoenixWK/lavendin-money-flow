package com.tracking_money_flow.user.infrastructure.persistence.mapper;

import com.tracking_money_flow.user.domain.PasswordRecovery;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.PasswordRecoveryJpaEntity;

public class PasswordRecoveryMapper {

    public static PasswordRecovery toDomain(PasswordRecoveryJpaEntity entity) {
        return PasswordRecovery.createWithAttachedId(
                entity.getAttachedId(),
                entity.getRequestBy(),
                entity.getIsExpired(),
                entity.getRequestedAt(),
                entity.getExpiredAt()
        );
    }

    public static PasswordRecoveryJpaEntity toEntity(PasswordRecovery passwordRecovery) {
        return new PasswordRecoveryJpaEntity(
                passwordRecovery.getAttachedId(),
                passwordRecovery.getRequestBy(),
                passwordRecovery.getIsExpired(),
                passwordRecovery.getRequestedAt(),
                passwordRecovery.getExpiredAt()
        );
    }
}
