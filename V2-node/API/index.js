const swaggerUi = require('swagger-ui-express');
const swaggerFile = require('./swagger-output.json');
const bodyParser = require('body-parser');
const express = require('express');
const app = express();

const { version } = require('mongoose');




/* Middlewares */
app.use(bodyParser.json());
app.use('/docs', swaggerUi.serve, swaggerUi.setup(swaggerFile));

app.listen(3000, () => {
  console.log("API documentation: http://localhost:3000/docs");
});

/* Endpoints */
require('./controllers/Address.controller')(app);
require('./controllers/Auth.controller')(app);
require('./controllers/User.controller')(app);