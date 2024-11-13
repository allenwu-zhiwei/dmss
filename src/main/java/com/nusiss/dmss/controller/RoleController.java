package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Role;
import com.nusiss.dmss.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(new ApiResponse<>(true, "Roles retrieved successfully", roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Role retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Role not found", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Role>> createRole(@RequestBody Role role) {
        Role savedRole = roleService.saveRole(role);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Role created successfully", savedRole));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable Long id, @RequestBody Role updatedRole) {
        Optional<Role> existingRole = roleService.getRoleById(id);
        if (existingRole.isPresent()) {
            updatedRole.setId(id);
            Role savedRole = roleService.saveRole(updatedRole);
            return ResponseEntity.ok(new ApiResponse<>(true, "Role updated successfully", savedRole));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Role not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable Long id) {
        Optional<Role> existingRole = roleService.getRoleById(id);
        if (existingRole.isPresent()) {
            roleService.deleteRole(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Role deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Role not found", null));
        }
    }
}
