package com.challenge.goku_e_commerce.dtos;

import com.challenge.goku_e_commerce.enums.UserRole;

public record UserDTO(String username, String login, String password, UserRole userRole) {
    
}
