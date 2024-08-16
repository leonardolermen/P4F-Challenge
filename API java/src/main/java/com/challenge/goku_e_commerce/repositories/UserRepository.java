package com.challenge.goku_e_commerce.repositories;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.challenge.goku_e_commerce.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findById(String id);
    Optional<User> findByLogin(String login);

}
