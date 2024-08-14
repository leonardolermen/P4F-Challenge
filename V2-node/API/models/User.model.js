const mongoose = require("mongoose");
const Address = require('./schemas/Addres.schema');

const userSchema = new mongoose.Schema({
  name: String,
  email: String,
  password: String,
  addresses: [Address]  
});

// adiciona um endereço
userSchema.methods.addAddress = function(newAddress) {
  this.addresses.push(newAddress);
  return this.save();  
};

// remove um endereço
userSchema.methods.removeAddress = function(addressId) {
  this.addresses = this.addresses.filter(address => address._id.toString() !== addressId.toString());
  return this.save();
}

module.exports = mongoose.model("User", userSchema);
