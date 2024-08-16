package com.challenge.goku_e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.challenge.goku_e_commerce.dtos.AddressDTO;
import com.challenge.goku_e_commerce.dtos.ViaCepDTO;
import com.challenge.goku_e_commerce.entities.Address;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ViaCepService {
    
    @Value("${viacep.api.url}")
    private String viaCepApiUrl;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public Address findByCep(String cep) {
        String url = viaCepApiUrl + "/" + cep + "/json";
        ResponseEntity<ViaCepDTO> response = restTemplate.getForEntity(url, ViaCepDTO.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ViaCepDTO viaCepResponse = response.getBody();
            AddressDTO data = new AddressDTO(viaCepResponse.localidade(), viaCepResponse.uf(),viaCepResponse.cep().replace("-", "").replace(" ", "").trim(), viaCepResponse.logradouro());

            Address address = new Address(data);
           
            return address;
        } else {
            throw new EntityNotFoundException("Address not found with CEP: " + cep);
        }
    }
}

