package dev.srinivas.UserService.service;

import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.repository.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for handling role-related operations.
 */
@Service
public class RoleService {

    private RoleRepository roleRepository;

    /**
     * Constructor for RoleService.
     * @param roleRepository the role repository
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Creates a new role.
     * @param name the name of the role
     * @return the created role
     */
    public Role createRole(String name) {
        Role role = new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }
}