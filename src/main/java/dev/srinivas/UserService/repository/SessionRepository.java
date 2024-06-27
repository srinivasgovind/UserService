package dev.srinivas.UserService.repository;

import dev.srinivas.UserService.model.Session;
import dev.srinivas.UserService.model.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for managing Session entities.
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    /**
     * Finds a session by its token and the user ID.
     * @param token the session token
     * @param userId the ID of the user
     * @return an Optional containing the session if found, or empty if not found
     */
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}