const swaggerAutogen = require('swagger-autogen')();
const fs = require('fs');
const path = require('path');

const doc = {
  info: {
    title: 'Node Goku-e-commerce API',
    description: 'User registration, login, and address management'
  },
  host: 'localhost:3000',
  schemes: ['http'],
  components: {
    securitySchemes: {
      bearerAuth: {
        type: 'http',
        scheme: 'bearer',
        bearerFormat: 'JWT'
      }
    }
  },
  tags: [
    {
      name: 'Users',
      description: 'Endpoints for user management'
    },
    {
      name: 'Addresses',
      description: 'Endpoints for address management'
    },
    {
      name: 'Auth',
      description: 'Endpoints for user authentication'
    }
  ]
};

const outputFile = './swagger-output.json';
const routes = [
  './controllers/User.controller.js',
  './controllers/Address.controller.js',
  './controllers/Auth.controller.js'
];

// Gerar o arquivo de documentação Swagger
swaggerAutogen(outputFile, routes, doc).then(() => {
  console.log('Swagger documentation generated!');
});
