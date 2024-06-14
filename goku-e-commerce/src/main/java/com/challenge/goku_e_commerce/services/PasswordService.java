package com.challenge.goku_e_commerce.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    public String encode(String password){
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        return encodedPassword;
    }
}
