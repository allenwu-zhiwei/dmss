package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.PermissionRepository;
import com.nusiss.dmss.entity.Permission;
import com.nusiss.dmss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission permission) {
        permission.setCreateDatetime(LocalDateTime.now());
        permission.setUpdateDatetime(LocalDateTime.now());
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission updatePermission(Long id, Permission permission) {
        Optional<Permission> existingPermission = permissionRepository.findById(id);
        if (existingPermission.isPresent()) {
            Permission updatedPermission = existingPermission.get();
            updatedPermission.setEndpoint(permission.getEndpoint());
            updatedPermission.setMethod(permission.getMethod());
            updatedPermission.setUpdateUser(permission.getUpdateUser());
            updatedPermission.setUpdateDatetime(LocalDateTime.now());
            return permissionRepository.save(updatedPermission);
        } else {
            throw new RuntimeException("Permission not found with id: " + id);
        }
    }

    @Override
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Set<Permission> findPermissionsByUserRoles(Integer userId) {
        // 调用 DAO 层方法查询权限集合
        return permissionRepository.findPermissionsByUserRoles(userId);
    }
}
