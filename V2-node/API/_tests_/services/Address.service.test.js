const axios = require('axios');
const { addressService } = require('../../services/Address.Service');
const Address = require('../../models/Address.model');
const addressCache = require('node-cache');

jest.mock('axios');

jest.mock()

describe('getAddresses', () => {
  const cep = '12345678';
  const cachedData = {
    zip: '12345678',
    street: 'Rua Exemplo',
    city: 'Cidade Exemplo',
    state: 'UF'
  };



  it('deve fazer uma requisição para a API Via CEP e retornar o endereço', async () => {
    const response = {
      data: {
        cep: '06454080',
        logradouro: 'Rua Exemplo',
        localidade: 'Cidade Exemplo',
        uf: 'UF'
      }
    };
    axios.get.mockResolvedValue(response);
    const req = { cep };
    const address = await addressService.getAddresses(req.cep);
    expect(address).toBeInstanceOf(Address);
    expect(address.zip).toBe(response.data.cep);
    expect(address.street).toBe(response.data.logradouro);
    expect(address.city).toBe(response.data.localidade);
    expect(address.state).toBe(response.data.uf);
  });

  it('deve lançar um erro se a requisição para a API Via CEP falhar', async () => {
    axios.get.mockRejectedValue(new Error('Erro na requisição'));
    const req = { cep };
    await expect(getAddresses(req)).rejects.toThrowError('Erro na requisição');
  });

  it('deve lançar um erro se o CEP for inválido', async () => {
    axios.get.mockResolvedValue({ data: null });
    const req = { cep };
    await expect(getAddresses(req)).rejects.toThrowError('Invalid CEP');
  });
});