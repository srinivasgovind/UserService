package dev.srinivas.UserService.security.repositories;



import dev.srinivas.UserService.security.models.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Authorization entities.
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, String> {

    /**
     * Finds an Authorization by its state.
     * @param state the state of the authorization
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByState(String state);

    /**
     * Finds an Authorization by its authorization code value.
     * @param authorizationCode the authorization code value
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);

    /**
     * Finds an Authorization by its access token value.
     * @param accessToken the access token value
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByAccessTokenValue(String accessToken);

    /**
     * Finds an Authorization by its refresh token value.
     * @param refreshToken the refresh token value
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByRefreshTokenValue(String refreshToken);

    /**
     * Finds an Authorization by its OIDC ID token value.
     * @param idToken the OIDC ID token value
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByOidcIdTokenValue(String idToken);

    /**
     * Finds an Authorization by its user code value.
     * @param userCode the user code value
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByUserCodeValue(String userCode);

    /**
     * Finds an Authorization by its device code value.
     * @param deviceCode the device code value
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    Optional<Authorization> findByDeviceCodeValue(String deviceCode);

    /**
     * Finds an Authorization by matching any of the token values.
     * @param token the token value to match
     * @return an Optional containing the Authorization if found, or empty if not found
     */
    @Query("select a from Authorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token" +
            " or a.userCodeValue = :token" +
            " or a.deviceCodeValue = :token"
    )
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);
}