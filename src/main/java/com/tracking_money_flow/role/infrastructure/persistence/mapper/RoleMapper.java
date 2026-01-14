package com.tracking_money_flow.role.infrastructure.persistence.mapper;

import com.tracking_money_flow.role.domain.Role;
import com.tracking_money_flow.role.infrastructure.persistence.jpa.RoleEntity;

public class RoleMapper {

    public static RoleEntity toEntity(Role role) {

        RoleEntity roleEntity = new RoleEntity();

        if(!role.isNew()) {
            roleEntity.setId(roleEntity.getId());
        }

        roleEntity.setName(roleEntity.getName());

        return roleEntity;
    }

    public static Role toDomain(RoleEntity roleEntity) {
        return Role.reconstruct(
                roleEntity.getId(),
                roleEntity.getName()
        );
    }
}
