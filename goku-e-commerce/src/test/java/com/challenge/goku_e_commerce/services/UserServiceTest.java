package com.challenge.goku_e_commerce.services;


import com.challenge.goku_e_commerce.dtos.UserDTO;
import com.challenge.goku_e_commerce.entities.Address;
import com.challenge.goku_e_commerce.entities.User;
import com.challenge.goku_e_commerce.enums.UserRole;
import com.challenge.goku_e_commerce.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Given
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId("1");
        user1.setUsername("user1");
        userList.add(user1);

        when(userRepository.findAll()).thenReturn(userList);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("user1", result.get(0).getUsername());
    }

    
    @Test
    void testCreateUser() {
        // Given
        UserDTO userDTO = new UserDTO("test@example.com", "password","testUser", UserRole.USER);
    
        when(passwordService.encode(any())).thenReturn("encodedPassword");
        when(userRepository.existsById(userDTO.login())).thenReturn(false);
    
        // When
        User createdUser = userService.createUser(userDTO);
    
        // Then
        assertNotNull(createdUser);
        assertEquals(userDTO.username(), createdUser.getUsername());
        assertEquals(userDTO.login(), createdUser.getLogin());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        // Given
        String login = ("existing@example.com");

        UserDTO userDTO = new UserDTO(login, login, login, null);


        when(userRepository.existsById(userDTO.login())).thenReturn(true);

        // When / Then
        assertThrows(EntityExistsException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void testDeleteUser() {
        // Given
        String userId = "1";
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Given
        String userId = "1";
        when(userRepository.existsById(userId)).thenReturn(false);

        // When / Then
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));
    }
    
    // Exemplo de teste para FindByLogin
    @Test
    void testFindByLogin() {
        // Given
        String login = "test@example.com";
        User mockUser = new User();
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(mockUser));

        // When
        Optional<User> result = userService.FindByLogin(login);

        // Then
        assertTrue(result.isPresent());
        assertEquals(mockUser, result.get());
    }
}

