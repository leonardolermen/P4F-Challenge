package com.challenge.goku_e_commerce.DTOs;

import com.challenge.goku_e_commerce.entities.Address;

public record UserDTO(String id, String username, String email, String password, Address address) {
    
}
