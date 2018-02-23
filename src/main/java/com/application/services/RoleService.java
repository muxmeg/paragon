package com.application.services;

import com.application.model.Role;
import com.application.repositories.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RolesRepository rolesRepository;

    public RoleService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public Role verifyPassword(String name, String password) {
        Optional<Role> role = rolesRepository.getRole(name);
        return role.isPresent() && role.get().getPassword().equals(password) ? role.get() : null;
    }

    public List<String> findRoleNamesByTeam(String team) {
        return rolesRepository.findRoleNamesByTeam(team);
    }
}
