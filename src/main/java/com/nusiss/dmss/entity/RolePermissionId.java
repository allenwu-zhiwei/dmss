package com.nusiss.dmss.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class RolePermissionId implements Serializable {

    private Long roleId; // 角色ID
    private Long permissionId; // 权限ID

    // 必须实现 equals 方法，用于比较两个对象是否相等
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 如果是同一个对象，直接返回 true
        if (o == null || getClass() != o.getClass()) return false; // 如果类型不同，返回 false
        RolePermissionId that = (RolePermissionId) o; // 强制类型转换
        return Objects.equals(roleId, that.roleId) && Objects.equals(permissionId, that.permissionId);
    }

    // 必须实现 hashCode 方法，用于生成哈希值
    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId); // 根据 roleId 和 permissionId 生成哈希值
    }
}
