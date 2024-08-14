const mongoose = require("mongoose");
const Address = require('./schemas/Addres.schema');

const userSchema = new mongoose.Schema({
  name: String,
  email: String,
  password: String,
  addresses: [Address]  // Certifique-se de que "Address" está correto, com "d" duplo
});

// Método para adicionar um endereço
userSchema.methods.addAddress = function(newAddress) {
  this.addresses.push(newAddress);
  return this.save();  // Salva o documento após adicionar o endereço
};

module.exports = mongoose.model("User", userSchema);
