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

import com.challenge.goku_e_commerce.dtos.AddAdressDTO;
import com.challenge.goku_e_commerce.dtos.UserDTO;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.exceptions.ResourceNotFoundException;
import com.challenge.goku_e_commerce.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "List All Users operation")
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = this.userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Register Users operation")
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO data) {
        User newUser = this.userService.createUser(data);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @Operation(summary = "Delete Users operation")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(("User not found: " + userId), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update Users operation")
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId,@Valid @RequestBody UserDTO data) {
        try {
            userService.updateUser(userId, data);
            return ResponseEntity.ok("User updated");
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
    }

    @Operation(summary = "Add Addresses to Users operation")
    @PostMapping("/add-address")
    public ResponseEntity<String> addAddress(@RequestBody AddAdressDTO data) {
        try{
            return ResponseEntity.ok(userService.addUserAddress(data.userId(), data.cep()));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User or address not found", HttpStatus.NOT_FOUND);
        }
    }
}
