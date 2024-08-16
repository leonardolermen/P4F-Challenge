package com.challenge.goku_e_commerce.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.challenge.goku_e_commerce.dtos.AddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String cep;    

    public Address(AddressDTO data) {
        this.cep = data.cep();
        this.city = data.city();
        this.state = data.state();
        this.street = data.street();
    }

    public Address(String street, String city, String cep, String state) {
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.street = street;
    }
}
