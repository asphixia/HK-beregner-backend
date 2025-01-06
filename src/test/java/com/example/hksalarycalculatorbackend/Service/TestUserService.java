package com.example.hksalarycalculatorbackend.Service;

import com.example.hksalarycalculatorbackend.model.Roles;
import com.example.hksalarycalculatorbackend.model.User;
import com.example.hksalarycalculatorbackend.repositories.UserRepository;
import com.example.hksalarycalculatorbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestUserService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenNewUser_whenCreateUser_thenUserIsSaved() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plaintextpassword");
        when(passwordEncoder.encode("plaintextpassword")).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("encodedpassword", createdUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenUsername_whenGetUserByUsername_thenUserIsReturned() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        User foundUser = userService.getUserByUsername(username);
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void givenInvalidId_whenUpdateUser_thenThrowsException() {
        UUID id = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> userService.updateUser(id, updatedUser));
        assertEquals("User with ID " + id + " not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void givenUsername_whenDeleteUser_thenUserIsDeleted() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        userService.deleteUser(username);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void givenNoUsers_whenGetAllUsers_thenEmptyListIsReturned() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        assertTrue(users.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void givenUsername_whenGetUserRole_thenRoleIsReturned() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setRole(Roles.ADMIN);
        when(userRepository.findByUsername(username)).thenReturn(user);
        Roles role = userService.getUserRole(username);
        assertNotNull(role);
        assertEquals(Roles.ADMIN, role);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void givenInvalidUsername_whenGetUserRole_thenThrowsException() {
        String username = "invaliduser";
        when(userRepository.findByUsername(username)).thenReturn(null);
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.getUserRole(username));
        assertEquals("User not found: " + username, exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}

