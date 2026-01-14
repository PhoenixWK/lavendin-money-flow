package com.tracking_money_flow.role.application.service;

import com.tracking_money_flow.role.application.command.CreateRoleCommand;
import com.tracking_money_flow.role.application.command.UpdateRoleCommand;
import com.tracking_money_flow.role.application.port.in.CreateRoleUseCase;
import com.tracking_money_flow.role.application.port.in.DeleteRoleUseCase;
import com.tracking_money_flow.role.application.port.in.GetAllRolesUseCase;
import com.tracking_money_flow.role.application.port.in.UpdateRoleUseCase;
import com.tracking_money_flow.role.domain.Role;

import java.util.List;


public class RoleService {

    private final CreateRoleUseCase createRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;

    public RoleService(CreateRoleUseCase createRoleUseCase, DeleteRoleUseCase deleteRoleUseCase, GetAllRolesUseCase getAllRolesUseCase, UpdateRoleUseCase updateRoleUseCase) {
        this.createRoleUseCase = createRoleUseCase;
        this.deleteRoleUseCase = deleteRoleUseCase;
        this.getAllRolesUseCase = getAllRolesUseCase;
        this.updateRoleUseCase = updateRoleUseCase;
    }

    public Role createRole(CreateRoleCommand command) {
        return createRoleUseCase.execute(Role.register(command.name()));
    }

    public Role updateRole(UpdateRoleCommand command) {
        return updateRoleUseCase.execute(Role.reconstruct(command.id(), command.name()));
    }

    public List<Role> getAllRoles() {
        return getAllRolesUseCase.execute();
    }

    public void deleteRole(UpdateRoleCommand command) {
        deleteRoleUseCase.execute(command.id());
    }
}
