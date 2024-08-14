const bcrypt = require('bcrypt');
const UserService = require('./User.service');
const jwt = require('jsonwebtoken');
require('dotenv').config();
const userService = new UserService();

class AuthService {
  async login(req, res) {
    try {
      const user = await userService.getUserByEmail(req.email);
      if (!user) {
        throw new Error('User not found');
      }

      const passwordMatch = await bcrypt.compare(req.password, user.password);
      if (!passwordMatch) {
        throw new Error('Invalid credentials');
      }

      // Gera o JWT token
      const token = jwt.sign({ userId: user._id, email: user.email }, process.env.SECRET_KEY, {
        expiresIn: '1h'
      });
      return token;
    } catch (error) {
      console.error(error);
      return res.status(401).json({ message: 'Invalid credentials' });
    }
  }
}

module.exports = AuthService;