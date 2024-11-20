package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by WHY on 2024/11/20.
 * Functions:
 */
@Entity
@Table(name = "notification_to_user")
@Getter
@Setter
public class NotificationToUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Integer notificationId;

    @Column(nullable = false)
    private Integer userId;

    // 表示通知
    @ManyToOne
    private Notifications notification;

    // 表示接收通知的用户
    @ManyToOne
    private User user;


    public NotificationToUser() {}

    public NotificationToUser(Integer notificationId, Integer userId, Notifications notification, User user) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.notification = notification;
        this.user = user;
    }

}
