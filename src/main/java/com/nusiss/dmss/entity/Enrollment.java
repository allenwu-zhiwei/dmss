package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 学生选课信息
 */
@Entity
@Table(name="Enrollment")
@Getter
@Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Enrollmentid;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)  // 数据库列名是 user_id
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)  // 数据库列名是 student_id
    private Course course;

    @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime enrollmentDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateDate;

    private String createUser;
    private String updateUser;




    public enum Status {
        NOT_ENROLLED,IN_PROGRESS,COMPLETED,DROPED
    }



}
