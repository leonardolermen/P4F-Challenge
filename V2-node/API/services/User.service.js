const User = require('../models/User.model');
const bcrypt = require('bcrypt');
const addresses = require('../models/Address.model');

class UserService {

  // cria um novo user
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


  // lista todos os users
  async getUsers() {
    return await User.find().exec();
  }

  // encontra um user pelo id
  async getUserById(id) {
    return await User.findById(id).exec();
  }

  async getUserByEmail(email) {
    return await User.findOne({ email }).exec();
  }

  async updateUser(id, data) {
    return await User.findByIdAndUpdate(id, data, { new: true }).exec();
  }

  async deleteUser(id) {
    await User.findByIdAndRemove(id).exec();
  }
}

module.exports = UserService;