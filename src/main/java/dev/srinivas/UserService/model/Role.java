package dev.srinivas.UserService.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a user role.
 */
@Getter
@Setter
@Entity
public class Role extends BaseModel {

    /**
     * The name of the role.
     */
    private String role;
}
