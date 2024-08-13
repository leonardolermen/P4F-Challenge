const { default: mongoose } = require("mongoose");
const Adress = require('./Addres.schema');

const userSchema = new mongoose.Schema({
    name: String,
    email: String,
    password: String,
    adresses: [Adress]
})
module.exports = userSchema;