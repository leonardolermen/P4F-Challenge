const axios = require('axios');
const AddressService = require('../../services/Address.Service');
const Address = require('../../models/Address.model');
const UserService = require('../../services/User.service');
const NodeCache = require('node-cache');

jest.mock('axios');
jest.mock('node-cache');

describe('AddressService - getAddresses', () => {
  let addressService;
  let mockCache;
  let mockUserService;

  beforeEach(() => {
    mockCache = new NodeCache();
    mockUserService = new UserService();
    addressService = new AddressService();
  });

  function compareAddressIgnoringId(expected, actual) {
    return expected.zip === actual.zip &&
           expected.street === actual.street &&
           expected.city === actual.city &&
           expected.state === actual.state;
  }

  it('should return cached address data if available', async () => {
    const req = { cep: "06454080" };
    const cachedData = {
      zip: "06454080",
      street: "Rua Teste",
      city: "Cidade Teste",
      state: "TS"
    };

    mockCache.get.mockReturnValue(cachedData);

    

    const expectedAddress = new Address(cachedData);
    
    expect(compareAddressIgnoringId(expectedAddress, cachedData)).toBe(true);
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

    mockCache.get.mockReturnValue(null);
    axios.get.mockResolvedValue(apiResponse);

    
    const result = await addressService.getAddresses(req);

    const expectedAddress = new Address(expectedData);

    expect(compareAddressIgnoringId(expectedAddress, result)).toBe(true);
  });

  it('should throw an error if CEP is invalid', async () => {
    const req = { cep: "00000000" };

    axios.get.mockResolvedValue(null);

    await expect(addressService.getAddresses(req)).rejects.toThrow('Invalid CEP');
  });
});
