package com.challenge.goku_e_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.challenge.goku_e_commerce.dtos.UserDTO;
import com.challenge.goku_e_commerce.entities.Address;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private AddressService addressService;

    @Cacheable(value = "users")
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(UserDTO data) {
        if(userRepository.existsById(data.login()) == true){
            throw new EntityExistsException("Email already exists: " + data.login());
        }
        String encodedPassword = this.passwordService.encode(data.password());
        User newUser = new User(data, encodedPassword);
        this.userRepository.save(newUser);
        return newUser;
    }

    @CacheEvict(value = "users", key = "#userId")

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        this.userRepository.deleteById(id);
    }
    
    @CachePut(value = "users", key = "#userId")
    public void updateUser(String userId, UserDTO data) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        existingUser.setUsername(data.username());
        if(userRepository.findByLogin(data.login()).isPresent()){
            throw new EntityExistsException("Email already exists: " + data.login());
        }
        existingUser.setLogin(data.login());
        existingUser.setPassword(passwordService.encode(data.password())); 

        userRepository.save(existingUser);
    }

    @CacheEvict(value = "users", allEntries = true)
    public String addUserAddress(String userId, String addresCep){
        User existingUser = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Address address = this.addressService.findByCep(addresCep);
        if(existingUser.getAddresses().contains(address)){
            return "Address already exists for the user";
        }
        existingUser.addAddress(address);

        userRepository.save(existingUser);
        return "Addres added to User";
    }
    @Cacheable(value = "users", key = "#login" )
    public Optional<User> FindByLogin(String login) {
       return userRepository.findByLogin(login);
    }
}
