package dev.srinivas.UserService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * Entity representing a user session.
 */
@Getter
@Setter
@Entity
public class Session extends BaseModel {

    /**
     * The session token.
     */
    private String token;

    /**
     * The expiration date and time of the session.
     */
    private Date expiringAt;

    /**
     * The login date and time of the session.
     */
    private Date loginAt;

    /**
     * The user associated with the session.
     */
    @ManyToOne
    private User user;

    /**
     * The status of the session.
     */
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
}