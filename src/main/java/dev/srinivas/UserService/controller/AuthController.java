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

    private AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request){
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logout(@PathVariable("id") Long userId, @RequestHeader("token") String token){
        return authService.logout(token,userId);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request){
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request){
        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());

        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }

    //below APIs are for testing purpose, Not for Business

    @GetMapping("/session")
    public ResponseEntity<List<Session>> getAllSession(){
        return authService.getAllSession();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return authService.getAllUsers();
    }
}
