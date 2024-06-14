package com.challenge.goku_e_commerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.goku_e_commerce.DTOs.UserDTO;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User createUser(UserDTO data){
        String encodedPassword = this.passwordService.encode(data.password());
        User newUser = new User(data, encodedPassword);
        return newUser;
    }
}
