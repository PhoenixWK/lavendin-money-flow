package com.tracking_money_flow.user.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    Optional<UserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
