package com.tracking_money_flow.user.application.port.out;

import com.tracking_money_flow.user.domain.PasswordRecovery;

import java.util.Optional;

public interface PasswordRecoveryRepository {
    Optional<PasswordRecovery> findById(String id);
    void updateExpiredStatus(String token);
    void save(PasswordRecovery passwordRecovery);
}
