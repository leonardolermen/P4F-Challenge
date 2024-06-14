package com.challenge.goku_e_commerce.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.challenge.goku_e_commerce.DTOs.UserDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@Document(collection = "table_users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    public User(UserDTO data, String encodedPassword) {
        this.addresses = data.addresses();
        this.email = data.email();
        this.password = encodedPassword;
        this.username = data.username();
    }
}
