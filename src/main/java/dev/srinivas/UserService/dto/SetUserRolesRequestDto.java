package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Data Transfer Object for setting user roles.
 */
@Getter
@Setter
public class SetUserRolesRequestDto {

    /**
     * The list of role IDs to be assigned to the user.
     */
    private List<Long> roleIds;
}
