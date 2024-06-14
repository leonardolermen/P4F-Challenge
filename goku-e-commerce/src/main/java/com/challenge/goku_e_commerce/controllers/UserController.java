package com.challenge.goku_e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.challenge.goku_e_commerce.DTOs.UserDTO;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.exceptions.ResourceNotFoundException;
import com.challenge.goku_e_commerce.services.UserService;

import jakarta.persistence.EntityNotFoundException;

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

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable String userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted");
        }
         catch( EntityNotFoundException e){
            return new ResponseEntity<>(("User not found: " + userId), HttpStatus.NOT_FOUND);
        }
    }

      @PutMapping("/{userId}")
        public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDTO data) {
        try {
            userService.updateUser(userId, data);
            return ResponseEntity.ok("User updated");
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }
}
