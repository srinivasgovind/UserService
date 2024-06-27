package dev.srinivas.UserService.security.repositories;


import dev.srinivas.UserService.security.models.AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing AuthorizationConsent entities.
 */
@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {

    /**
     * Finds an AuthorizationConsent by the registered client ID and principal name.
     * @param registeredClientId the ID of the registered client
     * @param principalName the name of the principal
     * @return an Optional containing the AuthorizationConsent if found, or empty if not found
     */
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * Deletes an AuthorizationConsent by the registered client ID and principal name.
     * @param registeredClientId the ID of the registered client
     * @param principalName the name of the principal
     */
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}