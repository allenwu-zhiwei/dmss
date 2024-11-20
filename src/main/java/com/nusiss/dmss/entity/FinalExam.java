package com.nusiss.dmss.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
//@Table(name = "final_exams")
public class FinalExam extends Exam {

    private static final String DEFAULT_TYPE = "FINAL";

    public FinalExam() {
        this.setExamType(DEFAULT_TYPE);
    }

    public FinalExam(Integer courseId, String examName, LocalDate examDate, Integer durationMinutes, String location) {
        super();
    }

    @Override
    public String toString() {
        return "FinalExam{" +
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
                '}';
    }
}
