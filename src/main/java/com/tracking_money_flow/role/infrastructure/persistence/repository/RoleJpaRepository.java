package com.tracking_money_flow.role.infrastructure.persistence.repository;

import com.tracking_money_flow.role.infrastructure.persistence.jpa.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
