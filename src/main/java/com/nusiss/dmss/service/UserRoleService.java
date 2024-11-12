package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.UserRoleRepository;
import com.nusiss.dmss.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 添加用户角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 添加成功的消息
     */
    public String addUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId); // 设置用户ID
        userRole.setRoleId(roleId); // 设置角色ID
        userRoleRepository.save(userRole); // 保存到数据库
        return "User role added successfully!";
    }

    /**
     * 查询用户的所有角色
     * @param userId 用户ID
     * @return 用户的角色列表
     */
    public List<UserRole> getUserRolesByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId); // 查询数据库中的角色列表
    }

    /**
     * 删除用户角色
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 删除成功的消息
     */
    public String deleteUserRole(Long userId, Long roleId) {
        UserRole.UserRoleId userRoleId = new UserRole.UserRoleId();
        userRoleId.setUserId(userId); // 设置用户ID
        userRoleId.setRoleId(roleId); // 设置角色ID
        userRoleRepository.deleteById(userRoleId); // 从数据库中删除
        return "User role deleted successfully!";
    }
}
