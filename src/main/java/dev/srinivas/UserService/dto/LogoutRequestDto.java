package dev.srinivas.UserService.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

@Getter
@Setter
public class LogoutRequestDto {
    private Long userId;
}
