const { default: mongoose } = require("mongoose");
const addressSchema = require('./schemas/Addres.schema');

const Address = mongoose.model('Address',addressSchema);

module.exports = Address;