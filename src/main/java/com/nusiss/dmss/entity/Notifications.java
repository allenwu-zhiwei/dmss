package com.nusiss.dmss.entity;

import com.nusiss.dmss.entity.observer.Observer;
import com.nusiss.dmss.entity.observer.Subject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Notifications")
@Getter
@Setter
public class Notifications implements Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    //@ManyToOne
    //@JoinColumn(name = "user_id",nullable = false)
    //private User user;
    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer isRead;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDatetime;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateDatetime;

    private String createUser;
    private String updateUser;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotificationToUser> notificationToUsers;

    @Override
    public void addObserver(Observer observer) {
        User user = (User) observer;
        NotificationToUser notificationToUser = new NotificationToUser(this.notificationId, user.getUserId(), this, user);
        notificationToUsers.add(notificationToUser);
    }

    @Override
    public void removeObserver(Observer observer) {
        User user = (User) observer;  // 强制转换为 User 类型
        notificationToUsers.removeIf(notificationToUser -> notificationToUser.getUserId().equals(user.getUserId()));
    }

    @Override
    public void notifyObservers() {
        // 通知所有用户
        for (NotificationToUser notificationToUser : notificationToUsers) {
            notificationToUser.getUser().update(this);  // 通知每个用户
        }
    }

    public void triggerNotification() {
        notifyObservers();
    }
}
