package com.nusiss.dmss.controller;

import com.nusiss.dmss.dao.UserRoleRepository;
import com.nusiss.dmss.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    @Autowired
    private UserRoleRepository userRoleRepository;

    // 添加用户角色
    @PostMapping
    public ResponseEntity<String> addUserRole(@RequestParam Long userId, @RequestParam Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.save(userRole);
        return ResponseEntity.ok("User role added successfully!");
    }

    // 查询用户的所有角色
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRole>> getUserRoles(@PathVariable Long userId) {
        List<UserRole> roles = userRoleRepository.findByUserId(userId);
        return ResponseEntity.ok(roles);
    }

    // 删除用户角色
    @DeleteMapping
    public ResponseEntity<String> deleteUserRole(@RequestParam Long userId, @RequestParam Long roleId) {
        UserRole.UserRoleId userRoleId = new UserRole.UserRoleId();
        userRoleId.setUserId(userId);
        userRoleId.setRoleId(roleId);
        userRoleRepository.deleteById(userRoleId);
        return ResponseEntity.ok("User role deleted successfully!");
    }
}
