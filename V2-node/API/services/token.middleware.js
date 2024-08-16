const jwt = require('jsonwebtoken');

function VerifyToken(secretKey) {
  return (req, res, next) => {
      const authHeader = req.headers['authorization'];
      if (!authHeader) {
          return res.status(403).send({ message: 'No token provided!' });
      }

      const token = authHeader.split(' ')[1];
      if (!token) {
          return res.status(403).send({ message: 'Invalid token format!' });
      }

      jwt.verify(token, secretKey, (err, decoded) => {
          if (err) {
              return res.status(403).send({ message: 'Failed to authenticate token!' });
          }

          req.userId = decoded.id;
          next();
      });
  };
}


module.exports = VerifyToken;
