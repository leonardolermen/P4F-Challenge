package com.challenge.goku_e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.goku_e_commerce.dtos.JwtDTO;
import com.challenge.goku_e_commerce.dtos.LoginDTO;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.services.AuthService;
import com.challenge.goku_e_commerce.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login operation")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data) {
        try {
            var accessToken =authService.login(data);
            return ResponseEntity.ok(new JwtDTO(accessToken));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("email or password incorrect", HttpStatus.UNAUTHORIZED);
        }
    }
}
