package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface PermissionRepository extends JpaRepository<Permission, Long> {


    @Query(value = "SELECT p.* FROM permissions p JOIN role_permissions rp ON rp.permission_id = p.id " +
            "            JOIN user_roles ur ON ur.role_id= rp.role_id WHERE ur.user_id = ?1", nativeQuery = true)
    Set<Permission> findPermissionsByUserRoles(Integer userId);

    Optional<Permission> findByEndpointAndMethod(String endpoint, String method);

    // 使用 JPA 自带的 saveAll 方法来进行批量插入
    <S extends Permission> List<S> saveAll(Iterable<S> permissions);
}
