const monsgoose = require('mongoose');
const userSchema = require('./schemas/User.schema');
const { default: mongoose } = require('mongoose');

const User = mongoose.model('User', userSchema);

module.exports = User;