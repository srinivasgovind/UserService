package dev.srinivas.UserService.controller;

import dev.srinivas.UserService.dto.SetUserRolesRequestDto;
import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    /**
     * Constructor for UserController.
     * @param userService the user service
     */
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Retrieves the details of a user by their ID.
     * @param userId the ID of the user
     * @return a ResponseEntity containing the user details
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserDetails(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Sets roles for a user.
     * @param userId the ID of the user
     * @param request contains the role IDs to be assigned to the user
     * @return a ResponseEntity containing the updated user details
     */
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request){
        UserDto userDto = userService.setUserRoles(userId, request.getRoleIds());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
