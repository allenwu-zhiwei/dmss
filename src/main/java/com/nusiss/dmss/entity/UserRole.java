package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "user_roles")
@IdClass(UserRole.UserRoleId.class) // 使用内部类定义联合主键
@Getter
@Setter
public class UserRole {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Getter
    @Setter
    public static class UserRoleId implements Serializable {
        private Long userId;
        private Long roleId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoleId that = (UserRoleId) o;
            return userId.equals(that.userId) && roleId.equals(that.roleId);
        }

        @Override
        public int hashCode() {
            return userId.hashCode() + roleId.hashCode();
        }
    }
}
