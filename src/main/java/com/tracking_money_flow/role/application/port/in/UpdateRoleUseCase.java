package com.tracking_money_flow.role.application.port.in;

import com.tracking_money_flow.role.application.port.out.RoleRepository;
import com.tracking_money_flow.role.domain.Role;

public class UpdateRoleUseCase {
    private final RoleRepository repo;

    public UpdateRoleUseCase(RoleRepository repo) {
        this.repo = repo;
    }

    public Role execute(Role role) {
         return repo.updateRole(role);
    }
}
