package com.vsouza.editora.controllers;

import com.vsouza.editora.config.security.TokenBuilder;
import com.vsouza.editora.config.security.UserDetailsImpl;
import com.vsouza.editora.dto.LoginRequest;
import com.vsouza.editora.dto.LoginResponse;
import com.vsouza.editora.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    TokenBuilder tokenBuilder;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for user: {}", loginRequest.getEmail());

        UserDetailsImpl userDetails = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        log.info("Login successful for user: {}", userDetails.getUser());

        // Gera o token JWT
        String token = tokenBuilder.generateToken(userDetails);
        log.info("Generated token for user: {}", userDetails.getUsername());

        // Prepara a resposta com o token e o tempo de expiração
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setExpires(tokenBuilder.getTokenExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
