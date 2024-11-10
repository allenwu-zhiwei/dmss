package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer examId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    @Column(name = "exam_name", length = 100)
    private String examName;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "exam_type", columnDefinition = "ENUM('MIDTERM', 'FINAL', 'OTHER') DEFAULT 'OTHER'")
    private String examType;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "status", columnDefinition = "ENUM('SCHEDULED', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED'")
    private String status;

    @Column(name = "create_datetime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime", columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateDatetime;

    @Column(name = "create_user", length = 255)
    private String createUser;

    @Column(name = "update_user", length = 255)
    private String updateUser;

    // Getters and Setters
}