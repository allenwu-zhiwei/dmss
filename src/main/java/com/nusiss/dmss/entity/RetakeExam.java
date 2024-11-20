package com.nusiss.dmss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
//@Table(name = "retake_exams")
public class RetakeExam extends Exam {

    private static final String DEFAULT_TYPE = "RETAKE";

    @Column(name = "retake_reason", length = 255)
    private String retakeReason; // 特有属性：补考原因

    @Column(name = "original_exam_date")
    private LocalDate originalExamDate; // 原考试日期

    public RetakeExam() {
        this.setExamType(DEFAULT_TYPE); // 设置默认类型为 RETAKE
    }

    // Constructor for creating RetakeExam instances
    public RetakeExam(Integer courseId, String examName, LocalDate examDate, String location, String retakeReason, LocalDate originalExamDate) {
        super();
        this.setCourseId(courseId);
        this.setExamName(examName);
        this.setExamDate(examDate);
        this.setLocation(location);
        this.setExamType(DEFAULT_TYPE);
        this.retakeReason = retakeReason;
        this.originalExamDate = originalExamDate;
    }

    // Getter and Setter for retakeReason
    public String getRetakeReason() {
        return retakeReason;
    }

    public void setRetakeReason(String retakeReason) {
        this.retakeReason = retakeReason;
    }

    // Getter and Setter for originalExamDate
    public LocalDate getOriginalExamDate() {
        return originalExamDate;
    }

    public void setOriginalExamDate(LocalDate originalExamDate) {
        this.originalExamDate = originalExamDate;
    }

    @Override
    public String toString() {
        return "RetakeExam{" +
                "examId=" + this.getExamId() +
                ", courseId=" + this.getCourseId() +
                ", examName='" + this.getExamName() + '\'' +
                ", examDate=" + this.getExamDate() +
                ", examType='" + this.getExamType() + '\'' +
                ", durationMinutes=" + this.getDurationMinutes() +
                ", location='" + this.getLocation() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                ", createDatetime=" + this.getCreateDatetime() +
                ", updateDatetime=" + this.getUpdateDatetime() +
                ", retakeReason='" + retakeReason + '\'' +
                ", originalExamDate=" + originalExamDate +
                '}';
    }
}
