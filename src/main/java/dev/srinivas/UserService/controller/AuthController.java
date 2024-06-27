package dev.srinivas.UserService.controller;

import dev.srinivas.UserService.dto.LoginRequestDto;
import dev.srinivas.UserService.dto.SignUpRequestDto;
import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.dto.ValidateTokenRequestDto;
import dev.srinivas.UserService.model.Session;
import dev.srinivas.UserService.model.SessionStatus;
import dev.srinivas.UserService.model.User;
import dev.srinivas.UserService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    /**
     * Constructor for AuthController.
     * @param authService the authentication service
     */
    private AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * Handles user login.
     * @param request contains the login credentials
     * @return a ResponseEntity containing the user details if login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request){
        return authService.login(request.getEmail(), request.getPassword());
    }

    /**
     * Handles user logout.
     * @param userId the ID of the user to log out
     * @param token the token to validate the logout request
     * @return a ResponseEntity with no content if logout is successful
     */
    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logout(@PathVariable("id") Long userId, @RequestHeader("token") String token){
        return authService.logout(token, userId);
    }

    /**
     * Handles user signup.
     * @param request contains the signup details
     * @return a ResponseEntity containing the newly created user details
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request){
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Validates the given token for the specified user.
     * @param request contains the token and user ID
     * @return a ResponseEntity containing the session status
     */
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request){
        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }

    // The following APIs are for testing purposes only and are not intended for production use.

    /**
     * Retrieves all sessions.
     * @return a ResponseEntity containing a list of all sessions
     */
//    @GetMapping("/session")
//    public ResponseEntity<List<Session>> getAllSession(){
//        return authService.getAllSession();
//    }

    /**
     * Retrieves all users.
     * @return a ResponseEntity containing a list of all users
     */
//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUsers(){
//        return authService.getAllUsers();
//    }
}
