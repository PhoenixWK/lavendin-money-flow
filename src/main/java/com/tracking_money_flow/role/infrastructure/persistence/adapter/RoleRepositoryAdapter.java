package com.tracking_money_flow.role.infrastructure.persistence.adapter;

import com.tracking_money_flow.role.application.port.out.RoleRepository;
import com.tracking_money_flow.role.domain.Role;
import com.tracking_money_flow.role.domain.exception.RoleAlreadyExistException;
import com.tracking_money_flow.role.domain.exception.RoleNotFoundException;
import com.tracking_money_flow.role.infrastructure.persistence.jpa.RoleEntity;
import com.tracking_money_flow.role.infrastructure.persistence.mapper.RoleMapper;
import com.tracking_money_flow.role.infrastructure.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleJpaRepository repo;


    @Override
    @Transactional
    public Role createRole(Role newRole) {
        if(repo.findByName(newRole.getName()).isPresent()) {
            throw new RoleAlreadyExistException("Role already exist");
        }

        repo.save(RoleMapper.toEntity(newRole));

        return newRole;
    }

    @Override
    @Transactional
    public Role updateRole(Role newRole) {
        RoleEntity role = repo.findById(newRole.getId())
                        .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        role.setName(newRole.getName());

        repo.save(role);

        return newRole;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new LinkedList<>();
        List<RoleEntity> rolesEntities = repo.findAll();

        for(RoleEntity en : rolesEntities) {
            roles.add(RoleMapper.toDomain(en));
        }

        return roles;
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        if(repo.findById(id).isEmpty()) {
            throw new RoleNotFoundException("Role not found");
        }

        repo.deleteById(id);
    }
}
