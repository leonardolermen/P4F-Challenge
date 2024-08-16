const jwt = require('jsonwebtoken');
const VerifyToken = require('../../services/token.middleware');

describe('VerifyToken Middleware', () => {
    let req, res, next;

    beforeEach(() => {
        req = { headers: {} };
        res = {
            status: jest.fn().mockReturnThis(),
            json: jest.fn()
        };
        next = jest.fn();
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    it('should return 401 if no token is provided', () => {
        const middleware = VerifyToken('mySecret');

        middleware(req, res, next);

        expect(res.status).toHaveBeenCalledWith(401);
        expect(res.json).toHaveBeenCalledWith({ error: 'Unauthorized' });
        expect(next).not.toHaveBeenCalled();
    });

    it('should return 401 if token is invalid', () => {
        req.headers['authorization'] = 'invalidToken';
        const middleware = VerifyToken('mySecret');

        jwt.verify = jest.fn((token, secret, callback) => {
            callback(new Error('Invalid token'), null);
        });

        middleware(req, res, next);

        expect(jwt.verify).toHaveBeenCalledWith('invalidToken', 'mySecret', expect.any(Function));
        expect(res.status).toHaveBeenCalledWith(401);
        expect(res.json).toHaveBeenCalledWith({ error: 'Unauthorized' });
        expect(next).not.toHaveBeenCalled();
    });

    it('should call next and set req.user if token is valid', () => {
        const validToken = 'validToken';
        const decodedUser = { id: '123', name: 'John Doe' };

        req.headers['authorization'] = validToken;
        const middleware = VerifyToken('mySecret');

        jwt.verify = jest.fn((token, secret, callback) => {
            callback(null, decodedUser);
        });

        middleware(req, res, next);

        expect(jwt.verify).toHaveBeenCalledWith(validToken, 'mySecret', expect.any(Function));
        expect(req.user).toEqual(decodedUser);
        expect(next).toHaveBeenCalled();
        expect(res.status).not.toHaveBeenCalled();
        expect(res.json).not.toHaveBeenCalled();
    });

    it('should use the default secret if no secret is provided', () => {
        const validToken = 'validToken';
        const decodedUser = { id: '123', name: 'John Doe' };

        req.headers['authorization'] = validToken;
        const middleware = VerifyToken();

        jwt.verify = jest.fn((token, secret, callback) => {
            callback(null, decodedUser);
        });

        middleware(req, res, next);

        expect(jwt.verify).toHaveBeenCalledWith(validToken, 'defaultSecret', expect.any(Function));
        expect(req.user).toEqual(decodedUser);
        expect(next).toHaveBeenCalled();
    });
});
