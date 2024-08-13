const { default: mongoose } = require("mongoose");

const AdressSchema = new mongoose.Schema({
    zip: String,
    state: String,
    city: String,
    street: String
});

module.exports = AdressSchema;