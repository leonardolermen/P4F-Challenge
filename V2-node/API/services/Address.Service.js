const Address = require('../models/Address.model');
const axios = require('axios');
const UserService = require('./User.service');
const cache = require('node-cache');
const userService = new UserService();

// Configurando o cache
const addressCache = new cache({ stdTTL: 600, checkperiod: 120 });

class AddressService {

    // Adiciona um endereço no user
    async addAddress(req) {
        try {
            const cep = req.cep;

            // Procura o cep no cache
            let cachedData = addressCache.get(cep);

            if (cachedData) {

                const user = await userService.getUserById(req.id);

                if (!user) {
                    throw new Error('User not found');
                }

                const address = new Address(cachedData);
                await user.addAddress(address);

                return user;
            }

            const user = await userService.getUserById(req.id);

            if (!user) {
                throw new Error('User not found');
            }

            // Se não estiver no cache, faz a requisição na API
            const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);

            if (response.data) {
                const data = {
                    zip: response.data.cep,
                    street: response.data.logradouro,
                    city: response.data.localidade,
                    state: response.data.uf
                };

                // Armazena o resultado no cache
                addressCache.set(cep, data);

                const address = new Address(data);
                await user.addAddress(address);

                return user;
            } else {
                throw new Error('Invalid CEP');
            }

        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    // Faz a requisição das informaçoes de um cep 
    async getAddresses(req) {
        const cep = req.cep;
        let cachedData = addressCache.get(cep);

        if(cachedData){
            const address = new Address(cachedData);
            return address;
        }

        const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);

        if (response.data) {
            const data = {
                zip: response.data.cep,
                street: response.data.logradouro,
                city: response.data.localidade,
                state: response.data.uf
            };
            addressCache.set(cep, data);

            const address = new Address(data);

            return address;
        } else {
            throw new Error('Invalid CEP');
        }
    }

    // Remove um endereço de um user
    async removeAddress(req) {
        try {
            const user = await userService.getUserById(req.userId);
            await user.removeAddress(req.addresId);
        } catch (error) {
            console.error(error);
            return error;
        }
    }
}

module.exports = AddressService;
