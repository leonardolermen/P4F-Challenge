const UserService = require('../../services/User.service');
const User = require('../../models/User.model');
const bcrypt = require('bcrypt');

jest.mock('bcrypt');
jest.mock('../../models/User.model');

describe('UserService', () => {
    let userService;

    beforeEach(() => {
        userService = new UserService();
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    describe('createUser', () => {
        it('should create a new user with hashed password', async () => {
            const data = { email: 'test@example.com', password: 'password123' };
            const hashedPassword = 'hashedPassword123';

            bcrypt.hash.mockResolvedValue(hashedPassword);
            data.password = hashedPassword;
            User.prototype.save = jest.fn().mockResolvedValue({ ...data, password: hashedPassword });

            const result = await userService.createUser(data);

            expect(bcrypt.hash).toHaveBeenCalledWith(data.password, 10);
            expect(User.prototype.save).toHaveBeenCalled();
            expect(result.password).toBe(hashedPassword);
        });
    });

    describe('getUsers', () => {
        it('should return all users', async () => {
            const users = [{ email: 'test1@example.com' }, { email: 'test2@example.com' }];
            User.find.mockReturnValue({ exec: jest.fn().mockResolvedValue(users) });

            const result = await userService.getUsers();

            expect(User.find).toHaveBeenCalled();
            expect(result).toEqual(users);
        });
    });

    describe('getUserById', () => {
        it('should return user by id', async () => {
            const user = { _id: '123', email: 'test@example.com' };
            User.findById.mockReturnValue({ exec: jest.fn().mockResolvedValue(user) });

            const result = await userService.getUserById('123');

            expect(User.findById).toHaveBeenCalledWith('123');
            expect(result).toEqual(user);
        });
    });

    describe('getUserByEmail', () => {
        it('should return user by email', async () => {
            const user = { _id: '123', email: 'test@example.com' };
            User.findOne.mockReturnValue({ exec: jest.fn().mockResolvedValue(user) });

            const result = await userService.getUserByEmail('test@example.com');

            expect(User.findOne).toHaveBeenCalledWith({ email: 'test@example.com' });
            expect(result).toEqual(user);
        });
    });

    describe('updateUser', () => {
        it('should update user by id', async () => {
            const user = { _id: '123', email: 'updated@example.com' };
            User.findByIdAndUpdate.mockReturnValue({ exec: jest.fn().mockResolvedValue(user) });

            const result = await userService.updateUser('123', { email: 'updated@example.com' });

            expect(User.findByIdAndUpdate).toHaveBeenCalledWith('123', { email: 'updated@example.com' }, { new: true });
            expect(result).toEqual(user);
        });
    });

    describe('deleteUser', () => {
        it('should delete user by id', async () => {
            // Mocking findByIdAndRemove para simular a remoção do usuário
            User.findByIdAndRemove = jest.fn().mockReturnValue({ exec: jest.fn() });

            await userService.deleteUser('123');

            expect(User.findByIdAndRemove).toHaveBeenCalledWith('123');
        });
    });

});
