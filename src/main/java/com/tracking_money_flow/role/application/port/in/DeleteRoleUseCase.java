package com.tracking_money_flow.role.application.port.in;

import com.tracking_money_flow.role.application.port.out.RoleRepository;

public class DeleteRoleUseCase {
    private final RoleRepository repo;

    public DeleteRoleUseCase(RoleRepository repo) {
        this.repo = repo;
    }

    public void execute(Long id) {
        repo.deleteRole(id);
    }
}
