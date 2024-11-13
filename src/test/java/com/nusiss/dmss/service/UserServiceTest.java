package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.UserRepository;
import com.nusiss.dmss.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findById(eq(1))).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void getUserByIdNotFound() {
        when(userRepository.findById(eq(1))).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void getUserByUsername() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findByUsername(eq("testuser"))).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void getUserByUsernameNotFound() {
        when(userRepository.findByUsername(eq("testuser"))).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByUsername("testuser");

        assertFalse(result.isPresent());
    }

    @Test
    void findUserByUsernameAndPassword() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findUserByUsernameAndPassword(eq("testuser"), eq("password"))).thenReturn(Collections.singletonList(user));

        List<User> result = userService.findUserByUsernameAndPassword("testuser", "password");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setUserId(1);
            return u;
        });

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(1, savedUser.getUserId());
        assertEquals("newuser", savedUser.getUsername());
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(eq(1));

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(eq(1));
    }
}