package dev.srinivas.UserService.mapper;

import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.model.User;

/**
 * Mapper class for converting User entities to User DTOs.
 */
public class UserEntityDTOMapper {

    /**
     * Converts a User entity to a UserDto.
     * @param user the User entity to convert
     * @return the converted UserDto
     */
    public static UserDto getUserDTOFromUserEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
