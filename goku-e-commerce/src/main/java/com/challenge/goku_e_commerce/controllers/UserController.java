package com.challenge.goku_e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.goku_e_commerce.DTOs.UserDTO;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO data){
        User newUser = this.userService.createUser(data);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
