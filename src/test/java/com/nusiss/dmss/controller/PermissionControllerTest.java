package com.nusiss.dmss.controller;

import com.nusiss.dmss.entity.Permission;
import com.nusiss.dmss.service.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PermissionControllerTest {

    @Mock
    private PermissionService permissionService;



    @InjectMocks
    private PermissionController permissionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(permissionController).build();
        objectMapper = new ObjectMapper(); // 初始化 ObjectMapper
    }

    @Test
    void createPermission() throws Exception {
        Permission permission = new Permission();
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionService.createPermission(any(Permission.class))).thenReturn(permission);

        mockMvc.perform(post("/api/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"endpoint\":\"/api/test\", \"method\":\"GET\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.endpoint").value("/api/test"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void getAllPermissions() throws Exception {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionService.getAllPermissions()).thenReturn(Collections.singletonList(permission));

        mockMvc.perform(get("/api/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].endpoint").value("/api/test"))
                .andExpect(jsonPath("$[0].method").value("GET"));
    }

    @Test
    void getPermissionById() throws Exception {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionService.getPermissionById(eq(1L))).thenReturn(Optional.of(permission));

        mockMvc.perform(get("/api/permissions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endpoint").value("/api/test"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void getPermissionByIdNotFound() throws Exception {
        when(permissionService.getPermissionById(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/permissions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePermission() throws Exception {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionService.updatePermission(eq(1L), any(Permission.class))).thenReturn(permission);

        mockMvc.perform(put("/api/permissions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"endpoint\":\"/api/test\", \"method\":\"GET\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endpoint").value("/api/test"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void deletePermission() throws Exception {
        doNothing().when(permissionService).deletePermission(eq(1L));

        mockMvc.perform(delete("/api/permissions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPermissionsByUserRoles() throws Exception {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setEndpoint("/api/test");
        permission.setMethod("GET");

        when(permissionService.findPermissionsByUserRoles(eq(1))).thenReturn(Collections.singleton(permission));

        mockMvc.perform(get("/api/permissions/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].endpoint").value("/api/test"))
                .andExpect(jsonPath("$[0].method").value("GET"));
    }

    @Test
    void saveAllPermissions() throws Exception {
        Permission permission1 = new Permission();
        permission1.setEndpoint("/api/test1");
        permission1.setMethod("GET");

        Permission permission2 = new Permission();
        permission2.setEndpoint("/api/test2");
        permission2.setMethod("POST");

        List<Permission> permissions = List.of(permission1, permission2);

        when(permissionService.saveAllPermissions(any(List.class))).thenReturn(permissions);

        mockMvc.perform(post("/api/permissions/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"endpoint\":\"/api/test1\", \"method\":\"GET\"}, {\"endpoint\":\"/api/test2\", \"method\":\"POST\"}]"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].endpoint").value("/api/test1"))
                .andExpect(jsonPath("$[0].method").value("GET"))
                .andExpect(jsonPath("$[1].endpoint").value("/api/test2"))
                .andExpect(jsonPath("$[1].method").value("POST"));
    }
}