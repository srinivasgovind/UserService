package dev.srinivas.UserService.mapper;

import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.model.User;

public class UserEntityDTOMapper {

    public static UserDto getUserDTOFromUserEntity(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;

    }
}
