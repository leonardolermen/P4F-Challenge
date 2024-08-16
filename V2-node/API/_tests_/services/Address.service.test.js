const axios = require('axios');
const AddressService  = require('../../services/Address.Service');
const Address = require('../../models/Address.model');
const UserService = require('../../services/User.service');
const NodeCache = require('node-cache');

jest.mock('axios');
jest.mock('../../models/Address.model');
jest.mock('../../services/User.service');
jest.mock('node-cache');

describe('AddressService - getAddresses', () => {
  let addressService;
  let mockCache;
  let mockUserService;

  beforeEach(() => {
    mockCache = new NodeCache();
    mockUserService = new UserService();
    addressService = new AddressService(mockUserService, mockCache);
  });

  it('should return cached address data if available', async () => {
    const req = { cep: "06454080" };
    const cachedData = {
      zip: "06454080",
      street: "Rua Teste",
      city: "Cidade Teste",
      state: "TS"
    };

    // Simula o comportamento do cache
    mockCache.get.mockReturnValue(cachedData);

    // Chama o método que queremos testar
    const result = await addressService.getAddresses(req);

    // Verifica se os dados retornados são os esperados
    expect(result).toEqual(new Address(cachedData));
    expect(mockCache.get).toHaveBeenCalledWith(req.cep);
  });

  it('should fetch and cache address data if not cached', async () => {
    const req = { cep: "06454080" };
    const apiResponse = {
      data: {
        cep: "06454080",
        logradouro: "Rua Teste",
        localidade: "Cidade Teste",
        uf: "TS"
      }
    };

    const expectedData = {
      zip: apiResponse.data.cep,
      street: apiResponse.data.logradouro,
      city: apiResponse.data.localidade,
      state: apiResponse.data.uf
    };

    // Simula o cache vazio e a resposta da API
    mockCache.get.mockReturnValue(undefined);
    axios.get.mockResolvedValue(apiResponse);

    // Chama o método que queremos testar
    const result = await addressService.getAddresses(req);

    // Verifica se os dados retornados e cacheados são os esperados
    expect(result).toEqual(new Address(expectedData));
    expect(mockCache.get).toHaveBeenCalledWith(req.cep);
    expect(axios.get).toHaveBeenCalledWith(`${process.env.VIA_CEP_API}${req.cep}/json/`);
    expect(mockCache.set).toHaveBeenCalledWith(req.cep, expectedData);
  });

  it('should throw an error if CEP is invalid', async () => {
    const req = { cep: "00000000" };

    // Simula uma resposta da API com erro
    axios.get.mockResolvedValue({ data: null });

    await expect(addressService.getAddresses(req)).rejects.toThrow('Invalid CEP');
  });
});
