package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.RoleRepository;
import com.nusiss.dmss.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoles() {
        // 准备数据
        when(roleRepository.findAll()).thenReturn(Arrays.asList(new Role(), new Role()));

        // 执行操作
        List<Role> roles = roleService.getAllRoles();

        // 验证结果
        assertNotNull(roles);
        assertEquals(2, roles.size());
    }

    @Test
    void getRoleById_Found() {
        // 准备数据
        Long id = 1L;
        Role role = new Role();
        role.setId(id);
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        // 执行操作
        Optional<Role> result = roleService.getRoleById(id);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void getRoleById_NotFound() {
        // 准备数据
        Long id = 1L;
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        // 执行操作
        Optional<Role> result = roleService.getRoleById(id);

        // 验证结果
        assertFalse(result.isPresent());
    }

    @Test
    void saveRole() {
        // 准备数据
        Role role = new Role();
        role.setId(1L);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        // 执行操作
        Role savedRole = roleService.saveRole(role);

        // 验证结果
        assertNotNull(savedRole);
        assertEquals(1L, savedRole.getId());
    }

    @Test
    void deleteRole() {
        // 准备数据
        Long id = 1L;
        doNothing().when(roleRepository).deleteById(id);

        // 执行操作
        roleService.deleteRole(id);

        // 验证删除操作
        verify(roleRepository, times(1)).deleteById(id);
    }
}
