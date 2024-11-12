package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "role_permissions")
@IdClass(RolePermission.RolePermissionId.class) // 使用内部类定义联合主键
@Getter
@Setter
public class RolePermission {

    @Id
    @Column(name = "role_id", nullable = false)
    private Long roleId; // 角色ID

    @Id
    @Column(name = "permission_id", nullable = false)
    private Long permissionId; // 权限ID

    // 内部类用于联合主键
    @Getter
    @Setter
    public static class RolePermissionId implements Serializable {
        private Long roleId;
        private Long permissionId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RolePermissionId that = (RolePermissionId) o;
            return roleId.equals(that.roleId) && permissionId.equals(that.permissionId);
        }

        @Override
        public int hashCode() {
            return roleId.hashCode() + permissionId.hashCode();
        }
    }
}
