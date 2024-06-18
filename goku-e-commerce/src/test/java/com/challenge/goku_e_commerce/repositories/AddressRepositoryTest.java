package com.challenge.goku_e_commerce.repositories;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.goku_e_commerce.entities.Address;

@ExtendWith(MockitoExtension.class)
public class AddressRepositoryTest {

    @Mock
    private AddressRepository addressRepository;

    @Test
    void testfindById(){
        String id = "123";
        Address address = new Address(id, "street", "city", "state", "cep");
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));

        Optional<Address> result = addressRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(address,result.get());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        String id = "123";
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Address> result = addressRepository.findById(id);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testfindByCep(){
        String cep = "12345678";
        Address address = new Address("123", "street", "city", "state", cep);
        when(addressRepository.findByCep(cep)).thenReturn(address);

        Address result = addressRepository.findByCep(cep);

        assertTrue(result != null);
        assertEquals(address,result);
    }

    @Test
    void testFindByCepNotFound() {
        // Arrange
        String cep = "12345678";
        when(addressRepository.findByCep(cep)).thenReturn(null);

        // Act
        Address result = addressRepository.findByCep(cep);

        // Assert
        assertTrue(result == null);
    }



}
