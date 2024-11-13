package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.config.CustomException;
import com.nusiss.dmss.service.JwtTokenService;
import com.nusiss.dmss.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("username", "testuser");
        requestParams.put("password", "testpassword");

        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("token", "mockToken");
        loginResponse.put("username", "testuser");
        loginResponse.put("userId", 1);
        loginResponse.put("role", "USER");

        when(loginService.login("testuser", "testpassword")).thenReturn(loginResponse);

        // Act
        ResponseEntity<ApiResponse<Map<String, Object>>> responseEntity = loginController.login(response, requestParams);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().isSuccess());
        assertEquals("Login successfully", responseEntity.getBody().getMessage());
        assertEquals("mockToken", responseEntity.getBody().getData().get("token"));
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("username", "invaliduser");
        requestParams.put("password", "wrongpassword");

        when(loginService.login("invaliduser", "wrongpassword")).thenThrow(new CustomException("Invalid username/password."));

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            loginController.login(response, requestParams);
        });

        assertEquals("Invalid username/password.", exception.getMessage());
    }
}
