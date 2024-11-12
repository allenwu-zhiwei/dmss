package com.nusiss.dmss.controller;

import com.nusiss.dmss.entity.Permission;
import com.nusiss.dmss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 创建新的权限记录
     * @param permission
     * @return
     */
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.createPermission(permission);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    /**
     * 获取所有权限记录
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    /**
     * 根据ID获取权限记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permissionService.getPermissionById(id);
        return permission.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 更新权限记录
     * @param id
     * @param permission
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        Permission updatedPermission = permissionService.updatePermission(id, permission);
        return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
    }

    /**
     * 删除权限记录
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据用户 ID 获取用户角色对应的权限集合
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<Permission>> getPermissionsByUserRoles(@PathVariable Integer userId) {
        Set<Permission> permissions = permissionService.findPermissionsByUserRoles(userId);
        return ResponseEntity.ok(permissions);
    }
}
