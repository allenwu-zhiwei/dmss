package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.PermissionRepository;
import com.nusiss.dmss.entity.Permission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPermission() {
        Permission permission = new Permission();
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionRepository.save(any(Permission.class))).thenAnswer(invocation -> {
            Permission p = invocation.getArgument(0);
            p.setId(1L);
            p.setCreateDatetime(LocalDateTime.now());
            p.setUpdateDatetime(LocalDateTime.now());
            return p;
        });

        Permission createdPermission = permissionService.createPermission(permission);

        assertNotNull(createdPermission);
        assertEquals(1L, createdPermission.getId());
        assertEquals("/api/test", createdPermission.getEndpoint());
        assertEquals("GET", createdPermission.getMethod());
        assertNotNull(createdPermission.getCreateDatetime());
        assertNotNull(createdPermission.getUpdateDatetime());
    }

    @Test
    void getAllPermissions() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionRepository.findAll()).thenReturn(Collections.singletonList(permission));

        List<Permission> permissions = permissionService.getAllPermissions();

        assertNotNull(permissions);
        assertFalse(permissions.isEmpty());
        assertEquals(1, permissions.size());
        assertEquals("/api/test", permissions.get(0).getEndpoint());
        assertEquals("GET", permissions.get(0).getMethod());
    }

    @Test
    void getPermissionById() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionRepository.findById(eq(1L))).thenReturn(Optional.of(permission));

        Optional<Permission> result = permissionService.getPermissionById(1L);

        assertTrue(result.isPresent());
        assertEquals("/api/test", result.get().getEndpoint());
        assertEquals("GET", result.get().getMethod());
    }

    @Test
    void getPermissionByIdNotFound() {
        when(permissionRepository.findById(eq(1L))).thenReturn(Optional.empty());

        Optional<Permission> result = permissionService.getPermissionById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void updatePermission() {
        Permission existingPermission = new Permission();
        existingPermission.setId(1L);
        existingPermission.setEndpoint("/api/old");
        existingPermission.setMethod("POST");

        Permission updatedPermission = new Permission();
        updatedPermission.setEndpoint("/api/new");
        updatedPermission.setMethod("GET");
        updatedPermission.setUpdateUser("admin");

        when(permissionRepository.findById(eq(1L))).thenReturn(Optional.of(existingPermission));
        when(permissionRepository.save(any(Permission.class))).thenAnswer(invocation -> {
            Permission p = invocation.getArgument(0);
            p.setUpdateDatetime(LocalDateTime.now());
            return p;
        });

        Permission result = permissionService.updatePermission(1L, updatedPermission);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("/api/new", result.getEndpoint());
        assertEquals("GET", result.getMethod());
        assertEquals("admin", result.getUpdateUser());
        assertNotNull(result.getUpdateDatetime());
    }

    @Test
    void deletePermission() {
        doNothing().when(permissionRepository).deleteById(eq(1L));

        permissionService.deletePermission(1L);

        verify(permissionRepository, times(1)).deleteById(eq(1L));
    }

    @Test
    void findPermissionsByUserRoles() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionRepository.findPermissionsByUserRoles(eq(1))).thenReturn(Collections.singleton(permission));

        Set<Permission> permissions = permissionService.findPermissionsByUserRoles(1);

        assertNotNull(permissions);
        assertFalse(permissions.isEmpty());
        assertEquals(1, permissions.size());
        assertEquals("/api/test", permissions.iterator().next().getEndpoint());
        assertEquals("GET", permissions.iterator().next().getMethod());
    }
}