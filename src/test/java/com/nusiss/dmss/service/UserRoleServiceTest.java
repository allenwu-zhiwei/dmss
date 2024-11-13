package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.UserRoleRepository;
import com.nusiss.dmss.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService;

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

        // 调用服务方法
        String result = userRoleService.addUserRole(1L, 2L);

        // 验证结果
        assertEquals("User role added successfully!", result);
        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void testGetUserRolesByUserId() {
        // 模拟数据
        UserRole role1 = new UserRole();
        role1.setUserId(1L);
        role1.setRoleId(2L);

        UserRole role2 = new UserRole();
        role2.setUserId(1L);
        role2.setRoleId(3L);

        when(userRoleRepository.findByUserId(1L)).thenReturn(Arrays.asList(role1, role2));

        // 调用服务方法
        List<UserRole> roles = userRoleService.getUserRolesByUserId(1L);

        // 验证结果
        assertNotNull(roles);
        assertEquals(2, roles.size());
        verify(userRoleRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testDeleteUserRole() {
        // 模拟数据
        UserRole.UserRoleId userRoleId = new UserRole.UserRoleId();
        userRoleId.setUserId(1L);
        userRoleId.setRoleId(2L);

        doNothing().when(userRoleRepository).deleteById(userRoleId);

        // 调用服务方法
        String result = userRoleService.deleteUserRole(1L, 2L);

        // 验证结果
        assertEquals("User role deleted successfully!", result);
        verify(userRoleRepository, times(1)).deleteById(userRoleId);
    }
}
