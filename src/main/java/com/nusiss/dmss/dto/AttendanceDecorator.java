package com.nusiss.dmss.dto;

import java.math.BigDecimal;

public class AttendanceDecorator implements StudentGradeComponent {
    private final StudentGradeComponent studentGradeComponent;
    private final Double attendanceRate;

    public AttendanceDecorator(StudentGradeComponent studentGradeComponent, Double attendanceRate) {
        this.studentGradeComponent = studentGradeComponent;
        this.attendanceRate = attendanceRate;
    }

    @Override
    public Integer getStudentId() {
        return studentGradeComponent.getStudentId();
    }

    @Override
    public String getStudentName() {
        return studentGradeComponent.getStudentName();
    }

    @Override
    public BigDecimal getGrade() {
        return studentGradeComponent.getGrade();
    }

    @Override
    public Double getAttendanceRate() {
        return attendanceRate;
    }
}
