package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for sign-up requests.
 */
@Getter
@Setter
public class SignUpRequestDto {

    /**
     * The email of the user signing up.
     */
    private String email;

    /**
     * The password of the user signing up.
     */
    private String password;
}
