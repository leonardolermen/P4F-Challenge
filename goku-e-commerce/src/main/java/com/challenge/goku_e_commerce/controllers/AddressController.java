package com.challenge.goku_e_commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.goku_e_commerce.dtos.AddressDTO;
import com.challenge.goku_e_commerce.entities.Address;
import com.challenge.goku_e_commerce.services.AddressService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = this.addressService.findAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{cep}")
    public ResponseEntity<Address> getByCep(@PathVariable String cep) {
        try {
            Address address = addressService.findByCep(cep);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressDTO data) {
        Address newAddress = this.addressService.createAddress(data);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable String addressId,@RequestBody AddressDTO data) {
        try {
            Address updatedAddress = addressService.updateAddress(addressId, data);
            return ResponseEntity.ok(updatedAddress);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String addresId){
        try {
            addressService.deleteById(addresId);
            return ResponseEntity.ok("Address deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Addres not found:" + addresId, HttpStatus.NOT_FOUND);
        }
    }

}
