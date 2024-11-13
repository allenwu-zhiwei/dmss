package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.RolePermissionRepository;
import com.nusiss.dmss.entity.RolePermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolePermissionServiceTest {

    @Mock
    private RolePermissionRepository rolePermissionRepository;

    @InjectMocks
    private RolePermissionService rolePermissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRolePermission() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(1L);
        rolePermission.setPermissionId(2L);

        when(rolePermissionRepository.save(any(RolePermission.class))).thenReturn(rolePermission);

        RolePermission result = rolePermissionService.addRolePermission(1L, 2L);

        assertNotNull(result);
        assertEquals(1L, result.getRoleId());
        assertEquals(2L, result.getPermissionId());
        verify(rolePermissionRepository, times(1)).save(any(RolePermission.class));
    }

    @Test
    void testGetPermissionsByRoleId() {
        RolePermission rolePermission1 = new RolePermission();
        rolePermission1.setRoleId(1L);
        rolePermission1.setPermissionId(2L);

        RolePermission rolePermission2 = new RolePermission();
        rolePermission2.setRoleId(1L);
        rolePermission2.setPermissionId(3L);

        when(rolePermissionRepository.findByRoleId(1L)).thenReturn(Arrays.asList(rolePermission1, rolePermission2));

        List<RolePermission> result = rolePermissionService.getPermissionsByRoleId(1L);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getPermissionId());
        assertEquals(3L, result.get(1).getPermissionId());
        verify(rolePermissionRepository, times(1)).findByRoleId(1L);
    }

    @Test
    void testGetRolesByPermissionId() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(1L);
        rolePermission.setPermissionId(2L);

        when(rolePermissionRepository.findByPermissionId(2L)).thenReturn(List.of(rolePermission));

        List<RolePermission> result = rolePermissionService.getRolesByPermissionId(2L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getRoleId());
        verify(rolePermissionRepository, times(1)).findByPermissionId(2L);
    }

    @Test
    void testDeleteRolePermission() {
        doNothing().when(rolePermissionRepository).delete(any(RolePermission.class));

        rolePermissionService.deleteRolePermission(1L, 2L);

        verify(rolePermissionRepository, times(1)).delete(any(RolePermission.class));
    }
}
