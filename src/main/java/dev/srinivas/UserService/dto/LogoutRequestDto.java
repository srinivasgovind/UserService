package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

/**
 * Data Transfer Object for logout requests.
 */
@Getter
@Setter
public class LogoutRequestDto {

    /**
     * The ID of the user attempting to log out.
     */
    private Long userId;
}
