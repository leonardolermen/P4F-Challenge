const express = require('express');
const router = express.Router();
const UserService = require('../services/User.service');
const userService = new UserService();

// Cria um novo usuário
router.post('/', async (req, res) => {
  try {
    const user = await userService.createUser(req.body);
    res.status(201).send({ message: 'User created', user });
  } catch (error) {
    console.error(error);
    res.status(400).send({ message: 'Error creating user', error: error.message });
  }
});

// Lista todos os usuários
router.get('/', async (req, res) => {
  try {
    const users = await userService.getUsers();
    res.send({ message: 'Users retrieved', users });
  } catch (error) {
    console.error(error);
    res.status(500).send({ message: 'Error retrieving users', error: error.message });
  }
});

// Atualiza um usuário pelo ID
router.put('/:id', async (req, res) => {
  try {
    const updatedUser = await userService.updateUser(req.params.id, req.body);
    res.status(200).send({ message: 'User updated', user: updatedUser });
  } catch (error) {
    console.error(error);
    res.status(404).send({ message: 'Error updating user', error: error.message });
  }
});

// Rota para deletar um usuário
router.delete('/:id', async (req, res) => {
  try {
    await userService.deleteUser(req.params.id);
    res.status(200).send({ message: 'User deleted' });
  } catch (error) {
    console.error(error);
    res.status(404).send({ message: 'User not found', error: error.message });
  }
});

module.exports = router;
