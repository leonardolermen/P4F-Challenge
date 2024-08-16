const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const UserService = require('../../services/User.service');
const AuthService = require('../../services/Auth.service');
require('dotenv').config();

// Mock dependencies
jest.mock('bcrypt');
jest.mock('jsonwebtoken');
jest.mock('../../services/User.service');

describe('AuthService', () => {
    let authService;
    let mockUserService;
    const mockToken = 'mockToken123';

    beforeEach(() => {
        authService = new AuthService();
        mockUserService = new UserService();
        process.env.SECRET_KEY = 'secret';
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    describe('login', () => {
        it('should return a JWT token when credentials are correct', async () => {
            const req = { email: 'test@example.com', password: 'password123' };
            const user = { _id: 'userId123', email: 'test@example.com', password: 'hashedPassword' };
            
            UserService.prototype.getUserByEmail.mockResolvedValue(user);
            bcrypt.compare.mockResolvedValue(true);
            jwt.sign.mockReturnValue(mockToken);

            const result = await authService.login(req, null);

            expect(UserService.prototype.getUserByEmail).toHaveBeenCalledWith(req.email);
            expect(bcrypt.compare).toHaveBeenCalledWith(req.password, user.password);
            expect(jwt.sign).toHaveBeenCalledWith(
                { userId: user._id, email: user.email },
                process.env.SECRET_KEY,
                { expiresIn: '1h' }
            );
            expect(result).toBe(mockToken);
        });

        it('should throw an error if the user is not found', async () => {
            const req = { email: 'test@example.com', password: 'password123' };

            UserService.prototype.getUserByEmail.mockResolvedValue(null);

            await expect(authService.login(req, null)).rejects.toThrow('Invalid credentials');
        });

        it('should throw an error if the password is invalid', async () => {
            const req = { email: 'test@example.com', password: 'password123' };
            const user = { _id: 'userId123', email: 'test@example.com', password: 'hashedPassword' };

            UserService.prototype.getUserByEmail.mockResolvedValue(user);
            bcrypt.compare.mockResolvedValue(false);

            await expect(authService.login(req, null)).rejects.toThrow('Invalid credentials');
        });
    });
});
