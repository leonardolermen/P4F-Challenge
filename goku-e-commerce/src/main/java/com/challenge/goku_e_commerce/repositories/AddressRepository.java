package com.challenge.goku_e_commerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.goku_e_commerce.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByCep(String cep);
}
