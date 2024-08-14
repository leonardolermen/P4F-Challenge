const express = require('express');
const router = express.Router();
const AuthService = require('../services/Auth.service');

const authService = new AuthService();

router.post('/', async (req, res) => {
    try {
        const token = await authService.login(req.body);
        res.status(200).send({message:"Sucessfull login", token});
    } catch (error) {
        res.status(500).send({message:"Fail login",error: error.message});
    }
});

module.exports = router;