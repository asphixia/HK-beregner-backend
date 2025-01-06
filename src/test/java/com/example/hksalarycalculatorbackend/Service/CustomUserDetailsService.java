package com.example.hksalarycalculatorbackend.Service;

import com.example.hksalarycalculatorbackend.model.Roles;
import com.example.hksalarycalculatorbackend.model.User;
import com.example.hksalarycalculatorbackend.repositories.UserRepository;
import com.example.hksalarycalculatorbackend.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestCustomUserDetailsService {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole(Roles.ADMIN);

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));

        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);
        
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("nonexistentuser"));

        assertEquals("User not found: nonexistentuser", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }
}