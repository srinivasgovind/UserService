package dev.srinivas.UserService.service;

import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name){
        Role role = new Role();
        role.setRole(name);
        return roleRepository.save(role);
    }

}
