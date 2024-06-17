package com.challenge.goku_e_commerce.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.challenge.goku_e_commerce.DTOs.AddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "address")
@Table(name = "address")
@Document(collection = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    private String id;
    private String street;
    private String city;
    private String state;
    private String cep;

    
    @JsonIgnore 
    

    public Address(AddressDTO data) {
        this.cep = data.cep();
        this.city = data.city();
        this.state = data.state();
        this.street = data.street();
    }
}
