package com.challenge.goku_e_commerce.entities;

import com.challenge.goku_e_commerce.DTOs.AddressDTO;

import jakarta.persistence.Id;
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
@Table(name = "addresses")
public class Address {
   
    @Id
    private String id;
    private String street;
    private String city;
    private String state;
    private String cep;

    public Address(AddressDTO data) {
        this.cep = data.cep();
        this.city = data.city();
        this.id = data.id();
        this.state = data.state();
        this.street = data.street();
    }
}
