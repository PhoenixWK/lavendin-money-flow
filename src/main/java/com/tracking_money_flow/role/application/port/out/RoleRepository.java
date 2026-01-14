package com.tracking_money_flow.role.application.port.out;

import com.tracking_money_flow.role.domain.Role;

import java.util.List;

public interface RoleRepository {
    Role createRole(Role newRole);
    Role updateRole(Role role);
    List<Role> getAllRoles();
    void deleteRole(Long id);
}
