package dev.srinivas.UserService.service;

import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.exception.InvalidCredentialException;
import dev.srinivas.UserService.exception.InvalidTokenException;
import dev.srinivas.UserService.exception.UserNotFoundException;
import dev.srinivas.UserService.mapper.UserEntityDTOMapper;
import dev.srinivas.UserService.model.Session;
import dev.srinivas.UserService.model.SessionStatus;
import dev.srinivas.UserService.model.User;
import dev.srinivas.UserService.repository.SessionRepository;
import dev.srinivas.UserService.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login (String email,String password){
        //Get User Details from DB
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
        jsonForJWT.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        String token = Jwts.builder()
                .claims(jsonForJWT) //added the claims
                .signWith(key,algorithm)  //added the algo and key
                .compact(); //building the token

        //session creation
        Session session = new Session();

        session.setLoginAt(new Date());
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = UserEntityDTOMapper.getUserDTOFromUserEntity(user);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, token);

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId){
        // validations -> token exists, token is not expired, user exists else throw exception
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            return null; // TODO throw exception here
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);

        return ResponseEntity.ok().build();

    }

    public UserDto signUp(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    public SessionStatus validate(String token, Long userId){
        //TODO check expiry // Jwts Parser -> parse the encoded JWT token to read the claims
        // Parses the JWT token and returns the claims
//        JwtParser jwtParser = Jwts.parser().build();
//        Jwt<?, ?> claims =jwtParser.parse(token);
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty() || sessionOptional.get().getSessionStatus().equals(SessionStatus.ENDED)){
            throw new InvalidTokenException("token is invalid");
        }

        return SessionStatus.ACTIVE;
    }

    public ResponseEntity<List<Session>> getAllSession(){
        List<Session> sessions = sessionRepository.findAll();
        return ResponseEntity.ok(sessions);
    }

    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
