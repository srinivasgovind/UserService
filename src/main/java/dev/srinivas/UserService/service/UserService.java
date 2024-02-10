package dev.srinivas.UserService.service;

import dev.srinivas.UserService.dto.UserDto;
import dev.srinivas.UserService.model.Role;
import dev.srinivas.UserService.model.User;
import dev.srinivas.UserService.repository.RoleRepository;
import dev.srinivas.UserService.repository.UserRepository;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository =  roleRepository;
    }

    public UserDto getUserDetails(Long userId){

        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
            return null;
        }
       return UserDto.from(userOptional.get());
    }


    public UserDto setUserRoles(Long userId, List<Long> roleIds){

        Optional<User> userOptional = userRepository.findById(userId);
        Set< Role> roles  = roleRepository.findAllByIdIn(roleIds);
        if(userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);

    }

}
