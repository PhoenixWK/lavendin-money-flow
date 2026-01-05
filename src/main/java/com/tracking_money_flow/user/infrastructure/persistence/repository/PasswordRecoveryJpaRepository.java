package com.tracking_money_flow.user.infrastructure.persistence.repository;


import com.tracking_money_flow.user.infrastructure.persistence.jpa.PasswordRecoveryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRecoveryJpaRepository extends JpaRepository<PasswordRecoveryJpaEntity, String> {

    Optional<PasswordRecoveryJpaEntity> findById(String id);

}
