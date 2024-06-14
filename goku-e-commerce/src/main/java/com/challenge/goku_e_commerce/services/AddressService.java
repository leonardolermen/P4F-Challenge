package com.challenge.goku_e_commerce.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.goku_e_commerce.DTOs.AddressDTO;
import com.challenge.goku_e_commerce.entities.Address;
import com.challenge.goku_e_commerce.repositories.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> findAll(){
       return this.addressRepository.findAll();
    }

    public Address createAddress(AddressDTO data){
        Address newAddress = new Address(data);
        return this.addressRepository.save(newAddress);
    }

    public Address findByCep(String cep){
      Address address =  this.addressRepository.findByCep(cep);
      return address;
    }
}
