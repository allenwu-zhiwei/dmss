package com.nusiss.dmss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.dmss.entity.RolePermission;
import com.nusiss.dmss.service.RolePermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolePermissionControllerTest {

    @Mock
    private RolePermissionService rolePermissionService;

    @InjectMocks
    private RolePermissionController rolePermissionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRolePermission() {
        // 模拟数据
        RolePermission mockRolePermission = new RolePermission();
        mockRolePermission.setRoleId(1L);
        mockRolePermission.setPermissionId(2L);

        when(rolePermissionService.addRolePermission(1L, 2L)).thenReturn(mockRolePermission);

        // 调用接口
        ResponseEntity<RolePermission> response = rolePermissionController.addRolePermission(1L, 2L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getRoleId());
        assertEquals(2L, response.getBody().getPermissionId());
    }

    @Test
    void testGetPermissionsByRoleId() {
        // 模拟数据
        RolePermission rolePermission1 = new RolePermission();
        rolePermission1.setRoleId(1L);
        rolePermission1.setPermissionId(2L);

        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setRoleId(1L);
        rolePermission2.setPermissionId(3L);

        List<RolePermission> permissions = Arrays.asList(rolePermission1, rolePermission2);
        when(rolePermissionService.getPermissionsByRoleId(1L)).thenReturn(permissions);

        // 调用接口
        ResponseEntity<List<RolePermission>> response = rolePermissionController.getPermissionsByRoleId(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetRolesByPermissionId() {
        // 模拟数据
        RolePermission rolePermission1 = new RolePermission();
        rolePermission1.setRoleId(1L);
        rolePermission1.setPermissionId(2L);

        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setRoleId(3L);
        rolePermission2.setPermissionId(2L);

        List<RolePermission> roles = Arrays.asList(rolePermission1, rolePermission2);
        when(rolePermissionService.getRolesByPermissionId(2L)).thenReturn(roles);

        // 调用接口
        ResponseEntity<List<RolePermission>> response = rolePermissionController.getRolesByPermissionId(2L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testDeleteRolePermission() {
        // 不需要返回值，只需验证调用
        doNothing().when(rolePermissionService).deleteRolePermission(1L, 2L);

        // 调用接口
        ResponseEntity<String> response = rolePermissionController.deleteRolePermission(1L, 2L);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Role-Permission mapping deleted successfully!", response.getBody());
    }
}
