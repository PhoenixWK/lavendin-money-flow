package com.tracking_money_flow.role.application.service;

import com.tracking_money_flow.role.application.command.CreateRoleCommand;
import com.tracking_money_flow.role.application.command.DeleteRoleCommand;
import com.tracking_money_flow.role.application.command.UpdateRoleCommand;
import com.tracking_money_flow.role.domain.Role;

import java.util.List;

public interface RoleService {
    Role createRole(CreateRoleCommand command);
    Role updateRole(UpdateRoleCommand command);
    List<Role> getAllRoles();
    void deleteRole(DeleteRoleCommand command);

}
