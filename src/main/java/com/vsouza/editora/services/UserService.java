package com.vsouza.editora.services;

import com.vsouza.editora.config.security.UserDetailsImpl;
import com.vsouza.editora.controllers.exceptions.BadRequestException;
import com.vsouza.editora.controllers.exceptions.ResourceNotFoundException;
import com.vsouza.editora.entities.User;
import com.vsouza.editora.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public UserDetailsImpl login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        log.info("LOGIN - user: {}", user);

        if (!user.isEnabled()){
            throw new BadRequestException("Invalid user!");
        }

        log.info("Login attempt classe userservice");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        log.info("LOGIN - passou aqui?? {}", authentication.getPrincipal());


        return new UserDetailsImpl(user);
    }
}
