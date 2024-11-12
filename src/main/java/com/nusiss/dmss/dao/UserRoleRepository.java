package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {
    List<UserRole> findByUserId(Long userId); // 根据userId查询用户的所有角色
}
