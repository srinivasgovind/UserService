package dev.srinivas.UserService.security;

import dev.srinivas.UserService.model.User;
import dev.srinivas.UserService.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
/**
 * CustomSpringUserDetailsService Impl.
 */
public class CustomSpringUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    public CustomSpringUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User details with given username is not found");
        }
        User savedUser = user.get();
        return new CustomSpringUserDetails(savedUser);
    }
}
