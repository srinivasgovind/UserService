package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for login requests.
 */
@Getter
@Setter
public class LoginRequestDto {

    /**
     * The email of the user attempting to log in.
     */
    private String email;

    /**
     * The password of the user attempting to log in.
     */
    private String password;
}
