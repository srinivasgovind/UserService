package dev.srinivas.UserService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user.
 */
@Getter
@Setter
@Entity(name = "APP_USER")
public class User extends BaseModel {

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The roles assigned to the user.
     */
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}

