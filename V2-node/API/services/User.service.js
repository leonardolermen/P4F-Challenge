const User = require('../models/User.model');
const bcrypt = require('bcrypt');
const addresses = require('../models/Address.model');

class UserService {

  // Cria um novo usuário
  async createUser(data) {
    const existingUser = await this.getUserByEmail(data.email);
    if (existingUser) {
      throw new Error('Email already used');
    }

    const hashedPassword = await bcrypt.hash(data.password, 10);

    const newUser = new User({ ...data, password: hashedPassword, addresses: [] });
    await newUser.save();

    return newUser;
  }

  // Lista todos os usuários
  async getUsers() {
    return await User.find().exec();
  }

  // Encontra um usuário pelo ID
  async getUserById(id) {
    return await User.findById(id).exec();
  }

  // Encontra um usuário pelo email
  async getUserByEmail(email) {
    return await User.findOne({ email }).exec();
  }

  // Atualiza um usuário
  async updateUser(id, data) {
    try {
      if (data.password) {
        data.password = await bcrypt.hash(data.password, 10);
      }

      const updatedUser = await User.findByIdAndUpdate(id, data, {
        new: true, // Retorna o documento atualizado
        runValidators: true // Executa as validações antes de atualizar
      }).exec();

      if (!updatedUser) {
        throw new Error('User not found');
      }

      return updatedUser;
    } catch (error) {
      // Fornece uma mensagem de erro mais detalhada
      console.error(`Error updating user with ID ${id}:`, error.message);
      throw new Error(`Failed to update user: ${error.message}`);
    }
  }

  // Deleta um usuário
  async deleteUser(id) {
    try {
      const result = await User.findByIdAndDelete(id).exec();
      if (!result) {
        throw new Error('User not found');
      }
      return result;
    } catch (error) {
      throw new Error('User not found');
    }
  }
}

module.exports = UserService;
