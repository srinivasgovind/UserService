package dev.srinivas.UserService.dto;

import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object for user details.
 */
@Getter
@Setter
public class UserDto {

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The roles assigned to the user.
     */
    private Set<Role> roles = new HashSet<>();

    /**
     * Converts a User entity to a UserDto.
     * @param user the User entity to convert
     * @return the converted UserDto
     */
    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
