const Address = require('../models/Address.model');
const axios = require('axios');
const UserService = require('./User.service');
const User = require('../models/User.model');

const userService = new UserService();

class AddressService {
    async createAddress(req) {
        try {
            const cep = req.cep;
            
            const user = await userService.getUserById(req.id);
            
            if (!user) {
                throw new Error('User not found');
            }

            const response = await axios.get(`https://viacep.com.br/ws/${cep}/json/`);

            if (response.data) {
                const data = {
                    zip: response.data.cep,
                    street: response.data.logradouro,
                    city: response.data.localidade,
                    state: response.data.uf
                };

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

    async getAddresses(){
        return await Address.find().exec();
    }
}

module.exports = AddressService;
