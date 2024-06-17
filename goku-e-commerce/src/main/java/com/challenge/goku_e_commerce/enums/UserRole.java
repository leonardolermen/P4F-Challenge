package com.challenge.goku_e_commerce.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("user");
  
    private String role;
  
    UserRole(String role) {
      this.role = role;
    }
  
    public String getValue() {
      return role;
    }
  }