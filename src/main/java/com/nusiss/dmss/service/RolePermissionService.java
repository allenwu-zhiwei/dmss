package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.RolePermissionRepository;
import com.nusiss.dmss.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    /**
     * 添加角色权限映射
     * @param roleId 角色ID
     * @param permissionId 权限ID
     * @return 添加的 RolePermission 对象
     */
    public RolePermission addRolePermission(Long roleId, Long permissionId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        return rolePermissionRepository.save(rolePermission); // 保存到数据库
    }

    /**
     * 根据角色ID获取权限
     * @param roleId 角色ID
     * @return 角色的权限列表
     */
    public List<RolePermission> getPermissionsByRoleId(Long roleId) {
        return rolePermissionRepository.findByRoleId(roleId); // 查询
    }

    /**
     * 根据权限ID获取关联的角色
     * @param permissionId 权限ID
     * @return 权限的角色列表
     */
    public List<RolePermission> getRolesByPermissionId(Long permissionId) {
        return rolePermissionRepository.findByPermissionId(permissionId); // 查询
    }

    /**
     * 删除角色权限映射
     * @param roleId 角色ID
     * @param permissionId 权限ID
     */
    public void deleteRolePermission(Long roleId, Long permissionId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        rolePermissionRepository.delete(rolePermission); // 删除
    }
}
