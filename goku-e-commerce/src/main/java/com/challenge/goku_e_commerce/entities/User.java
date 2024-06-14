package com.challenge.goku_e_commerce.entities;

import com.challenge.goku_e_commerce.DTOs.UserDTO;

import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    private String email;
    
    @OneToOne
    private Address address; 

    public User(UserDTO data, String encodedPassword) {
        this.address = data.address();
        this.email = data.email();
        this.id = data.id();
        this.password = encodedPassword;
        this.userName = data.username();
    }
}
