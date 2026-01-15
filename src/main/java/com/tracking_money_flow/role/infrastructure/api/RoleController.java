package com.tracking_money_flow.role.infrastructure.api;

import com.tracking_money_flow.common.response.Response;
import com.tracking_money_flow.role.application.command.CreateRoleCommand;
import com.tracking_money_flow.role.application.command.DeleteRoleCommand;
import com.tracking_money_flow.role.application.command.UpdateRoleCommand;
import com.tracking_money_flow.role.application.service.RoleServiceImpl;
import com.tracking_money_flow.role.domain.Role;
import com.tracking_money_flow.role.infrastructure.api.request.CreateRoleRequest;
import com.tracking_money_flow.role.infrastructure.api.request.UpdateRowRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
//@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {
    private final RoleServiceImpl roleServiceImpl;

    public RoleController(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<Response<Role>>  createRole(@RequestBody CreateRoleRequest request) {
        Role savedRole = roleServiceImpl.createRole(new CreateRoleCommand(request.name()));

        return ResponseEntity.ok(
                Response.<Role>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("New role created successfully")
                        .data(savedRole)
                        .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Response<Role>> updateRole(@RequestBody UpdateRowRequest request) {
        Role savedRole = roleServiceImpl.updateRole(new UpdateRoleCommand(request.id(), request.name()));

        return ResponseEntity.ok(
                Response.<Role>builder()
                        .status(HttpStatus.OK.value())
                        .message("Role updated successfully")
                        .data(savedRole)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<Response<List<Role>>> getAllRoles() {
        List<Role> roles = roleServiceImpl.getAllRoles();

        return ResponseEntity.ok(
                Response.<List<Role>>builder()
                        .status(HttpStatus.OK.value())
                        .message("All roles are retrieved successfully")
                        .data(roles)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<?>> deleteRole(@PathVariable Long id) {
        roleServiceImpl.deleteRole(new DeleteRoleCommand(id));

        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK.value())
                        .message("role are retrieved successfully")
                        .build()
        );
    }
}
