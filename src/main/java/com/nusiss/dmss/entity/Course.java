package com.nusiss.dmss.entity;

import com.nusiss.dmss.enumeration.Department;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 课程信息
 */
@Entity
@Table(name = "Courses")
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Column(unique = true, nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String courseDescription;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime courseStartDate;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime courseEndDate;

    @Column(nullable = false)
    private Integer credits;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateDate;

    private String createUser;
    private String updateUser;

    public  enum Status {
        ACTIVE, INACTIVE
    }
}
