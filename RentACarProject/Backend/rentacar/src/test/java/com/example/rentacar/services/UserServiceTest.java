package com.example.rentacar.services;

import com.example.rentacar.models.User;
import com.example.rentacar.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("rawpassword");
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void saveUser_ShouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode("rawpassword")).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals("encodedpassword", savedUser.getPassword());
        verify(passwordEncoder, times(1)).encode("rawpassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_ShouldCallRepositoryDelete() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateUser_ShouldUpdateUserDetails() {
        User updatedUser = new User();
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser("testuser", updatedUser);

        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());
        verify(userRepository, times(1)).save(user);
    }
}