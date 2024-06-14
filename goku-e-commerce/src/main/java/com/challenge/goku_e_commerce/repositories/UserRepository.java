package com.challenge.goku_e_commerce.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.challenge.goku_e_commerce.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    
}
