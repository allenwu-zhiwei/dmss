package com.nusiss.dmss.service;

import com.nusiss.dmss.entity.Permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionService {

    Permission createPermission(Permission permission);

    List<Permission> getAllPermissions();

    Optional<Permission> getPermissionById(Long id);

    Permission updatePermission(Long id, Permission permission);

    void deletePermission(Long id);

    // 根据用户 ID 查找用户角色对应的权限集合
    Set<Permission> findPermissionsByUserRoles(Integer userId);
}
