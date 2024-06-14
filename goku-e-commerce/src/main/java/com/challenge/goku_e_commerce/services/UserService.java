package com.challenge.goku_e_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.goku_e_commerce.DTOs.UserDTO;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(UserDTO data) {
        String encodedPassword = this.passwordService.encode(data.password());
        User newUser = new User(data, encodedPassword);
        return this.userRepository.save(newUser);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        this.userRepository.deleteById(id);
    }

    public void updateUser(String userId, UserDTO data) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        existingUser.setUsername(data.username());
        existingUser.setEmail(data.email());
        existingUser.setPassword(passwordService.encode(data.password())); 
        existingUser.setAddresses(data.addresses());

        userRepository.save(existingUser);
    }
}
