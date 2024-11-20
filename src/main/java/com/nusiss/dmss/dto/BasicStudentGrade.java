package com.nusiss.dmss.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BasicStudentGrade implements StudentGradeComponent {
    private Integer studentId;
    private String studentName;
    private BigDecimal grade;

    public BasicStudentGrade(Integer studentId, String studentName, BigDecimal grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
    }

    @Override
    public Double getAttendanceRate() {
        return null; // 基础类不包含出勤率，默认返回 null
    }
}
