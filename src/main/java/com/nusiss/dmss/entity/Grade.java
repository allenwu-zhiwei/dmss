package com.nusiss.dmss.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "course_id", nullable = false)
    private Integer courseId;

    @Column(name = "grade", precision = 5, scale = 2)
    private BigDecimal grade;

    @Column(name = "grading_date")
    private LocalDate gradingDate;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

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
