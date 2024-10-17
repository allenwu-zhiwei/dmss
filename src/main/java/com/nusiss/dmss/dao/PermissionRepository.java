package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;


public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "SELECT p.* FROM permissions p JOIN role_permissions rp ON rp.permission_id = p.id " +
            "            JOIN user_roles ur ON ur.role_id= rp.role_id WHERE ur.user_id = ?1", nativeQuery = true)
    Set<Permission> findPermissionsByUserRoles(Integer userId);
}
