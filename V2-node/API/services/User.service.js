const User = require('../models/User.model');
const bcrypt = require('bcrypt');
const addresses = require('../models/Address.model');

class UserService{
    async createUser(data) {
      const hashedPassword = await bcrypt.hash(data.password, 10);
      const user = new User({ ...data, password: hashedPassword,addresses:[] });
      await user.save();
      return user;
    }


    async getUsers() {
        return await user.find().exec();
    }

    async getUserById(id) {
        return await User.findById(id).exec();
      }
    
      async updateUser(id, data) {
        return await User.findByIdAndUpdate(id, data, { new: true }).exec();
      }
    
      async deleteUser(id) {
        await User.findByIdAndRemove(id).exec();
      }
}

module.exports = UserService;