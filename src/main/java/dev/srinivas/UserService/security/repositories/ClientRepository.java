package dev.srinivas.UserService.security.repositories;


import dev.srinivas.UserService.security.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Client entities.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    /**
     * Finds a Client by its client ID.
     * @param clientId the client ID
     * @return an Optional containing the Client if found, or empty if not found
     */
    Optional<Client> findByClientId(String clientId);
}