package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Role;
import com.nusiss.dmss.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleControllerTest {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoles() {
        // 准备数据
        when(roleService.getAllRoles()).thenReturn(Arrays.asList(new Role(), new Role()));

        // 执行操作
        ResponseEntity<ApiResponse<List<Role>>> response = roleController.getAllRoles();

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Roles retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getRoleById_Found() {
        // 准备数据
        Long id = 1L;
        Role role = new Role();
        role.setId(id);
        when(roleService.getRoleById(id)).thenReturn(Optional.of(role));

        // 执行操作
        ResponseEntity<ApiResponse<Role>> response = roleController.getRoleById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getId());
    }

    @Test
    void getRoleById_NotFound() {
        // 准备数据
        Long id = 1L;
        when(roleService.getRoleById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Role>> response = roleController.getRoleById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void createRole() {
        // 准备数据
        Role role = new Role();
        role.setId(1L);
        when(roleService.saveRole(any(Role.class))).thenReturn(role);

        // 执行操作
        ResponseEntity<ApiResponse<Role>> response = roleController.createRole(role);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(1L, response.getBody().getData().getId());
    }

    @Test
    void updateRole_Found() {
        // 准备数据
        Long id = 1L;
        Role updatedRole = new Role();
        updatedRole.setId(id);
        when(roleService.getRoleById(id)).thenReturn(Optional.of(new Role()));
        when(roleService.saveRole(any(Role.class))).thenReturn(updatedRole);

        // 执行操作
        ResponseEntity<ApiResponse<Role>> response = roleController.updateRole(id, updatedRole);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getId());
    }

    @Test
    void updateRole_NotFound() {
        // 准备数据
        Long id = 1L;
        Role updatedRole = new Role();
        updatedRole.setId(id);
        when(roleService.getRoleById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Role>> response = roleController.updateRole(id, updatedRole);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteRole_Found() {
        // 准备数据
        Long id = 1L;
        when(roleService.getRoleById(id)).thenReturn(Optional.of(new Role()));
        doNothing().when(roleService).deleteRole(id);

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = roleController.deleteRole(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Role deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteRole_NotFound() {
        // 准备数据
        Long id = 1L;
        when(roleService.getRoleById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = roleController.deleteRole(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}
