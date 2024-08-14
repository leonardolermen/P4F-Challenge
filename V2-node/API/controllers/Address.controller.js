const express = require('express');
const router = express.Router();
const AddressService = require('../services/Address.Service');

const addressService = new AddressService();

router.post('/', async (req,res) => {
    try {
        const user = await addressService.createAddress(req.body);
        res.status(201).send({ message: 'Address added successfully', user});
    } catch (error) {
        res.status(400).send({ message: 'Error cep or User not found', error: error.message });
    }
})

router.get('/', async (req,res) => {
    try {
        const addresses = await addressService.getAddresses();
        res.status(200).send(addresses);
    } catch (error) {
        
    }
})

module.exports = router;