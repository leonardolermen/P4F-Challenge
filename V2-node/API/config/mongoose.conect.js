const mongoose = require('mongoose');

mongoose.connect('mongodb://mongo:27017/Node_API')
.then(() => {
    console.log('Connected')
}).catch((error) => {
    console.error("Error connecting", error);
});