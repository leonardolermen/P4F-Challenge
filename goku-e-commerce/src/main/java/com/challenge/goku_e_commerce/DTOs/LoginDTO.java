package com.challenge.goku_e_commerce.dtos;

import com.challenge.goku_e_commerce.enums.UserRole;

public record LoginDTO(String login, String password, UserRole role) {
    
}
