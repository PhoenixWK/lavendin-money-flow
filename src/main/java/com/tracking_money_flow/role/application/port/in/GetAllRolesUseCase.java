package com.tracking_money_flow.role.application.port.in;

import com.tracking_money_flow.role.application.port.out.RoleRepository;
import com.tracking_money_flow.role.domain.Role;

import java.util.List;

public class GetAllRolesUseCase {
    private final RoleRepository repo;

    public GetAllRolesUseCase(RoleRepository repo) {
        this.repo = repo;
    }

    public List<Role> execute() {
        return repo.getAllRoles();
    }
}
