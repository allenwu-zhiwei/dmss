package com.nusiss.dmss.controller;

import com.nusiss.dmss.dao.UserRoleRepository;
import com.nusiss.dmss.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleControllerTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleController userRoleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUserRole() {
        // 模拟数据
        UserRole userRole = new UserRole();
        userRole.setUserId(1L);
        userRole.setRoleId(2L);

        when(userRoleRepository.save(any(UserRole.class))).thenReturn(userRole);

        // 调用控制器方法
        ResponseEntity<String> response = userRoleController.addUserRole(1L, 2L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User role added successfully!", response.getBody());
        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void testGetUserRoles() {
        // 模拟数据
        UserRole role1 = new UserRole();
        role1.setUserId(1L);
        role1.setRoleId(2L);

        UserRole role2 = new UserRole();
        role2.setUserId(1L);
        role2.setRoleId(3L);

        when(userRoleRepository.findByUserId(1L)).thenReturn(Arrays.asList(role1, role2));

        // 调用控制器方法
        ResponseEntity<List<UserRole>> response = userRoleController.getUserRoles(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(userRoleRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testDeleteUserRole() {
        // 模拟数据
        UserRole.UserRoleId userRoleId = new UserRole.UserRoleId();
        userRoleId.setUserId(1L);
        userRoleId.setRoleId(2L);

        doNothing().when(userRoleRepository).deleteById(userRoleId);

        // 调用控制器方法
        ResponseEntity<String> response = userRoleController.deleteUserRole(1L, 2L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User role deleted successfully!", response.getBody());
        verify(userRoleRepository, times(1)).deleteById(userRoleId);
    }
}
