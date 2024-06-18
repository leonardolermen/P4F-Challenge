package com.challenge.goku_e_commerce.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.goku_e_commerce.entities.User;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindById() {
        // Arrange
        String id = "123";
        User user = new User(id, "user", "password", "user@example.com", null, null);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepository.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        String id = "123";
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepository.findById(id);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByLogin() {
        // Arrange
        String login = "user@example.com";
        User user = new User("123", "user", "password", login, null, null);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepository.findByLogin(login);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByLoginNotFound() {
        // Arrange
        String login = "user";
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userRepository.findByLogin(login);

        // Assert
        assertTrue(result.isEmpty());
    }
}