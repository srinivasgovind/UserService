package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for creating a new role.
 */
@Getter
@Setter
public class CreateRoleRequestDto {

        /**
         * The name of the role to be created.
         */
        private String name;
}
