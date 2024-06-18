package com.challenge.goku_e_commerce.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.challenge.goku_e_commerce.dtos.AddressDTO;
import com.challenge.goku_e_commerce.entities.Address;
import com.challenge.goku_e_commerce.repositories.AddressRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Cacheable(value = "addresses")
    public List<Address> findAll(){
       return this.addressRepository.findAll();
    }

    public Address createAddress(AddressDTO data){
        Address newAddress = new Address(data);
        this.addressRepository.save(newAddress);
        return newAddress;
    }

    @Cacheable(value = "addresses", key = "#cep")
    public Address findByCep(String cep) {
        return Optional.ofNullable(addressRepository.findByCep(cep))
                .orElseThrow(() -> new EntityNotFoundException("Address not found with CEP: " + cep));
    }

    public Address updateAddress(String addressId, AddressDTO data) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId));

        existingAddress.setStreet(data.street());
        existingAddress.setCity(data.city());
        existingAddress.setState(data.state());
        existingAddress.setCep(data.cep());

        addressRepository.save(existingAddress);
        return existingAddress;
    }

    public void deleteById(String id){
        if(!addressRepository.existsById(id)){
            throw new EntityNotFoundException(id);
        }
        addressRepository.deleteById(id);
    }

}
