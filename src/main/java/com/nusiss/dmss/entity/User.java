package com.nusiss.dmss.entity;

import com.nusiss.dmss.entity.observer.Observer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Setter
@Getter
public class User implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDatetime;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateDatetime;

    private String createUser;
    private String updateUser;

    // Getters and Setters

    public enum Role {
        STUDENT, TEACHER, ADMIN, ADMINISTRATOR
    }

    @Override
    public void update(Notifications notification) {
        System.out.println("User " + username + " has received a notification: " + notification.getMessage());
    }

    public void subscribeToNotification(Notifications notification) {
        notification.addObserver(this);
    }

    public void unsubscribeFromNotification(Notifications notification) {
        notification.removeObserver(this);
    }
}
