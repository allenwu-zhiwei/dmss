package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.User;
import com.nusiss.dmss.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect(jsonPath("$.data[0].username").value("testuser"));
    }

    @Test
    void getUserById() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.getUserById(eq(1))).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User retrieved successfully"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    @Test
    void getUserByIdNotFound() throws Exception {
        when(userService.getUserById(eq(1))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("password");

        when(userService.saveUser(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setUserId(1);
            return u;
        });

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\", \"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.username").value("newuser"));
    }

    @Test
    void updateUser() throws Exception {
        User existingUser = new User();
        existingUser.setUserId(1);
        existingUser.setUsername("olduser");
        existingUser.setPassword("oldpassword");

        User updatedUser = new User();
        updatedUser.setUsername("newuser");
        updatedUser.setPassword("newpassword");

        when(userService.getUserById(eq(1))).thenReturn(Optional.of(existingUser));
        when(userService.saveUser(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setUserId(1);
            return u;
        });

        mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\", \"password\":\"newpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User updated successfully"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.username").value("newuser"));
    }

    @Test
    void updateUserNotFound() throws Exception {
        when(userService.getUserById(eq(1))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newuser\", \"password\":\"newpassword\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userService.getUserById(eq(1))).thenReturn(Optional.of(user));

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    void deleteUserNotFound() throws Exception {
        when(userService.getUserById(eq(1))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}