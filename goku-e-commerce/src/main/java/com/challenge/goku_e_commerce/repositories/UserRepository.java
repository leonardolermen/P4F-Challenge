package com.challenge.goku_e_commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.goku_e_commerce.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
}
