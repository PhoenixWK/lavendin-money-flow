package com.tracking_money_flow.role.infrastructure.config;

import com.tracking_money_flow.role.application.port.in.CreateRoleUseCase;
import com.tracking_money_flow.role.application.port.in.DeleteRoleUseCase;
import com.tracking_money_flow.role.application.port.in.GetAllRolesUseCase;
import com.tracking_money_flow.role.application.port.in.UpdateRoleUseCase;
import com.tracking_money_flow.role.application.port.out.RoleRepository;
import com.tracking_money_flow.role.application.service.RoleService;
import com.tracking_money_flow.role.application.service.RoleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    CreateRoleUseCase createRoleUseCase(RoleRepository repo) {
        return new CreateRoleUseCase(repo);
    }

    @Bean
    DeleteRoleUseCase deleteRoleUseCase(RoleRepository repo) {
        return new DeleteRoleUseCase(repo);
    }

    @Bean
    GetAllRolesUseCase getAllRolesUseCase(RoleRepository repo) {
        return new GetAllRolesUseCase(repo);
    }

    @Bean
    UpdateRoleUseCase updateRoleUseCase(RoleRepository repo) {
        return new UpdateRoleUseCase(repo);
    }

    @Bean
    RoleServiceImpl roleServiceImpl(
            CreateRoleUseCase createRoleUseCase,
            DeleteRoleUseCase deleteRoleUseCase,
            GetAllRolesUseCase getAllRolesUseCase,
            UpdateRoleUseCase updateRoleUseCase
    ) {
        return new RoleServiceImpl(
                createRoleUseCase,
                deleteRoleUseCase,
                getAllRolesUseCase,
                updateRoleUseCase
        );
    }
}
