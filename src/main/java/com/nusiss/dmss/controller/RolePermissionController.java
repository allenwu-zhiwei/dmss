package com.nusiss.dmss.controller;

import com.nusiss.dmss.entity.RolePermission;
import com.nusiss.dmss.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 添加角色权限映射
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 添加的 RolePermission 对象
     */
    @PostMapping
    public ResponseEntity<RolePermission> addRolePermission(@RequestParam Long roleId, @RequestParam Long permissionId) {
        RolePermission rolePermission = rolePermissionService.addRolePermission(roleId, permissionId);
        return ResponseEntity.ok(rolePermission);
    }

    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<RolePermission>> getPermissionsByRoleId(@PathVariable Long roleId) {
        List<RolePermission> permissions = rolePermissionService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

    /**
     * 根据权限ID查询角色
     * @param permissionId 权限ID
     * @return 角色列表
     */
    @GetMapping("/permission/{permissionId}")
    public ResponseEntity<List<RolePermission>> getRolesByPermissionId(@PathVariable Long permissionId) {
        List<RolePermission> roles = rolePermissionService.getRolesByPermissionId(permissionId);
        return ResponseEntity.ok(roles);
    }

    /**
     * 删除角色权限映射
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 成功消息
     */
    @DeleteMapping
    public ResponseEntity<String> deleteRolePermission(@RequestParam Long roleId, @RequestParam Long permissionId) {
        rolePermissionService.deleteRolePermission(roleId, permissionId);
        return ResponseEntity.ok("Role-Permission mapping deleted successfully!");
    }
}
