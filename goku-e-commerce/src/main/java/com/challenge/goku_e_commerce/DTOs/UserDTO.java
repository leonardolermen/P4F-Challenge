package com.challenge.goku_e_commerce.DTOs;

import com.challenge.goku_e_commerce.enums.UserRole;

public record UserDTO(String id, String username, String login, String password, UserRole userRole) {
    
}
