package dev.srinivas.UserService.service;

import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.model.User;
import dev.srinivas.UserService.repository.RoleRepository;
import dev.srinivas.UserService.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    /**
     * Constructor for UserService.
     * @param userRepository the user repository
     * @param roleRepository the role repository
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves the details of a user by their ID.
     * @param userId the ID of the user
     * @return the user details as a UserDto
     */
    public UserDto getUserDetails(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }
        return UserDto.from(userOptional.get());
    }

    /**
     * Sets roles for a user.
     * @param userId the ID of the user
     * @param roleIds the list of role IDs to be assigned to the user
     * @return the updated user details as a UserDto
     */
    public UserDto setUserRoles(Long userId, List<Long> roleIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        Set<Role> roles = roleRepository.findAllByIdIn(roleIds);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }
}