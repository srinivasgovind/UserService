package dev.srinivas.UserService.service;

import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.exception.InvalidCredentialException;
import dev.srinivas.UserService.exception.InvalidTokenException;
import dev.srinivas.UserService.exception.UserNotFoundException;
import dev.srinivas.UserService.mapper.UserEntityDTOMapper;
import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.model.Session;
import dev.srinivas.UserService.model.SessionStatus;
import dev.srinivas.UserService.model.User;
import dev.srinivas.UserService.repository.SessionRepository;
import dev.srinivas.UserService.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Service class for handling authentication-related operations.
 */
@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor for AuthService.
     * @param userRepository the user repository
     * @param sessionRepository the session repository
     * @param bCryptPasswordEncoder the password encoder
     */
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Handles user login by validating credentials and generating a JWT token.
     * @param email the user's email
     * @param password the user's password
     * @return a ResponseEntity containing the user details and a JWT token
     */
    public ResponseEntity<UserDto> login(String email, String password){
        // Get User Details from DB
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with the given email id does not exist");
        }

        User user = userOptional.get();

        //verify the user password given at the time of login with DB
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialException("Invalid Credentials");
        }
        //This random string generation token was used for temporary purpose
        //String token = RandomStringUtils.randomAlphanumeric(30);

        //Moving to JWT Token
        //Token Generation
        MacAlgorithm algorithm = Jwts.SIG.HS256; //HS256 algo added for JWT
        SecretKey key = algorithm.key().build(); //generating the secret key

        //start adding the claims
        Map<String,Object> jsonForJWT = new HashMap<>();
        jsonForJWT.put("userId", user.getId());
        jsonForJWT.put("roles", user.getRoles());
        jsonForJWT.put("createdAt", new Date());
        LocalDate localDate = LocalDate.now().plusDays(3);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Date expiryDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        jsonForJWT.put("expiryAt",expiryDate);

        String token = Jwts.builder()
                .claims(jsonForJWT) //added the claims
                .signWith(key,algorithm)  //added the algo and key
                .compact(); //building the token


        // Session creation
        Session session = new Session();
        session.setLoginAt(new Date());
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        session.setExpiringAt(expiryDate);
        sessionRepository.save(session);

        UserDto userDto = UserEntityDTOMapper.getUserDTOFromUserEntity(user);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, token);

        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    /**
     * Handles user logout by invalidating the session.
     * @param token the JWT token
     * @param userId the user's ID
     * @return a ResponseEntity indicating the logout status
     */
    public ResponseEntity<Void> logout(String token, Long userId){
        // validations -> token exists, token is not expired, user exists else throw exception
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            throw new InvalidTokenException("Token is invalid or user does not exist");
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    /**
     * Handles user sign-up by creating a new user in the database.
     * @param email the user's email
     * @param password the user's password
     * @return the created user's details
     */
    public UserDto signUp(String email, String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            User savedUser = userRepository.save(user);
            return UserDto.from(savedUser);
        }
        return null;
    }

    /**
     * Validates a JWT token and checks its status.
     * @param token the JWT token
     * @param userId the user's ID
     * @return the session status
     */
    public SessionStatus validate(String token, Long userId){
        // Parses the JWT token and returns the claims
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty() || sessionOptional.get().getSessionStatus().equals(SessionStatus.ENDED)){
            throw new InvalidTokenException("Token is invalid");
        }

        // Return the active session status
        return SessionStatus.ACTIVE;
    }

    /**
     * Retrieves all sessions.
     * @return a ResponseEntity containing a list of all sessions
     */
    public ResponseEntity<List<Session>> getAllSession(){
        List<Session> sessions = sessionRepository.findAll();
        return ResponseEntity.ok(sessions);
    }

    /**
     * Retrieves all users.
     * @return a ResponseEntity containing a list of all users
     */
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}