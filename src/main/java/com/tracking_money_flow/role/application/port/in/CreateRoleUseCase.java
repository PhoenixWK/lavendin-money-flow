package com.tracking_money_flow.role.application.port.in;

import com.tracking_money_flow.role.application.port.out.RoleRepository;
import com.tracking_money_flow.role.domain.Role;

public class CreateRoleUseCase {
    private final RoleRepository repo;

    public CreateRoleUseCase(RoleRepository repo) {
        this.repo = repo;
    }

    public Role execute(Role newRole) {
        return repo.createRole(newRole);
    }
}
