package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.RolePermission;
import com.nusiss.dmss.entity.RolePermission.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    // 根据 roleId 查询权限
    List<RolePermission> findByRoleId(Long roleId);

    // 根据 permissionId 查询角色
    List<RolePermission> findByPermissionId(Long permissionId);
}
