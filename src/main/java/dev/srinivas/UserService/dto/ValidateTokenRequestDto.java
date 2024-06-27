package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for token validation requests.
 */
@Getter
@Setter
public class ValidateTokenRequestDto {

    /**
     * The ID of the user whose token is being validated.
     */
    private Long userId;

    /**
     * The token to be validated.
     */
    private String token;
}
