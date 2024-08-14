const express = require('express');
const router = express.Router();
const UserService = require('../services/User.service');
const userService = new UserService();

router.post('/', async (req, res) => {
  try {
    const user = await userService.createUser(req.body);
    res.status(201).send({ message: 'User created', user });
  } catch (error) {
    console.error(error);
    res.status(400).send({ message: 'Error creating user', error: error.message });
  }
});

router.get('/', async (req, res) => {
  try {
    const users = await userService.getUsers();
    res.send({ message: 'Users retrieved', users });
  } catch (error) {
    console.error(error);
    res.status(500).send({ message: 'Error retrieving users', error: error.message });
  }
});

module.exports = router;