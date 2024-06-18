package com.challenge.goku_e_commerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.goku_e_commerce.dtos.AddressDTO;
import com.challenge.goku_e_commerce.entities.Address;
import com.challenge.goku_e_commerce.repositories.AddressRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    void testFindAll() {
        // Given
        AddressDTO data = new AddressDTO("city", "state", "cep", "street");
        List<Address> addresses = List.of(new Address(data));
        when(addressRepository.findAll()).thenReturn(addresses);

        // When
        List<Address> result = addressService.findAll();

        // Then
        assertEquals(addresses, result);
        verify(addressRepository).findAll();
    }

    @Test
    void testCreateAddress() {
        // Given
        AddressDTO data = new AddressDTO("street", "city", "state", "cep");
        Address newAddress = new Address(data);
        when(addressRepository.save(any())).thenReturn(newAddress);

        // When
        Address result = addressService.createAddress(data);

        // Then
        assertEquals(newAddress, result);
        verify(addressRepository).save(any());
    }

    @Test
    void testFindByCep() {
        // Given
        String cep = "cep";
        AddressDTO data = new AddressDTO("street", "city", "state", "cep");
        Address address = new Address(data);
        when(addressRepository.findByCep(cep)).thenReturn(address);

        // When
        Address result = addressService.findByCep(cep);

        // Then
        assertEquals(address, result);
        verify(addressRepository).findByCep(cep);
    }

    @Test
    void testFindByCepNotFound() {
        // Given
        String cep = "cep";
        when(addressRepository.findByCep(cep)).thenReturn(null);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> addressService.findByCep(cep));

        // Then
        assertEquals("Address not found with CEP: " + cep, exception.getMessage());
    }

    @Test
    void testUpdateAddress() {
        // Given
        String addressId = "id";
        AddressDTO addressDTO = new AddressDTO("street", "city", "state", "cep");
        Address existingAddress = new Address("old street", "old city", "old state", "old cep");
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));

        // When
        Address result = addressService.updateAddress(addressId, addressDTO);

        // Then
        assertEquals(addressDTO.street(), result.getStreet());
        assertEquals(addressDTO.city(), result.getCity());
        assertEquals(addressDTO.state(), result.getState());
        assertEquals(addressDTO.cep(), result.getCep());
        verify(addressRepository).save(existingAddress);
    }

    @Test
    void testUpdateAddressNotFound() {
        // Given
        String addressId = "id";
        AddressDTO addressDTO = new AddressDTO("street", "city", "state", "cep");
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> addressService.updateAddress(addressId, addressDTO));

        // Then
        assertEquals("Address not found with id: " + addressId, exception.getMessage());
    }

    @Test
    void testDeleteById() {
        // Given
        String id = "id";
        when(addressRepository.existsById(id)).thenReturn(true);

        // When
        addressService.deleteById(id);

        // Then
        verify(addressRepository).deleteById(id);
    }

    @Test
    void testDeleteByIdNotFound() {
        // Given
        String id = "id";
        when(addressRepository.existsById(id)).thenReturn(false);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> addressService.deleteById(id));

        // Then
        assertEquals(id, exception.getMessage());
    }
}