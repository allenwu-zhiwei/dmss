package com.nusiss.dmss.dto;

import java.math.BigDecimal;

public interface StudentGradeComponent {
    Integer getStudentId();
    String getStudentName();
    BigDecimal getGrade();
    Double getAttendanceRate();
}
