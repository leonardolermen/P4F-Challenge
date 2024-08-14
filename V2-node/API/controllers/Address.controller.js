const express = require('express');
const router = express.Router();
const AddressService = require('../services/Address.Service');

const addressService = new AddressService();

// como não tem numero de residência no endereço não há necessidade de ter um route.update

// Adiciona um endereço a um usuário
router.post('/', async (req, res) => {
    try {
        const user = await addressService.addAddress(req.body);
        res.status(201).send({ message: 'Address added successfully', user });
    } catch (error) {
        res.status(400).send({ message: 'Error Addres not added', error: error.message });
    }
})

// faz a consulta de um cep na API do ViaCep
router.get('/', async (req, res) => {
    try {
        const address = await addressService.getAddresses(req.body);
        res.status(200).send(address);
    } catch (error) {
        res.status(500).send({ message: "Error retrieving Addres", error: error.message })
    }
})

// remove um endereço de um usuário
router.delete('/', async (req, res) => {
    try {
        const user = await addressService.removeAddress(req.body);
        res.status(200).send({ message: "Address removed sucessfully", user });
    } catch (error) {
        res.status(400).send({ message: "Error, Address not removed", error: error.message })
    }
})


module.exports = router;