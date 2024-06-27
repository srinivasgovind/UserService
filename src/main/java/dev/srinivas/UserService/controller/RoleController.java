package dev.srinivas.UserService.controller;

import dev.srinivas.UserService.dto.CreateRoleRequestDto;
import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    /**
     * Constructor for RoleController.
     * @param roleService the role service
     */
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    /**
     * Handles the creation of a new role.
     * @param request contains the details for the new role
     * @return a ResponseEntity containing the created role
     */
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto request){
        Role role = roleService.createRole(request.getName());
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
