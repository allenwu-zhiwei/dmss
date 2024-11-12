package com.nusiss.dmss.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseReportDTO {
    private List<StudentGradeDTO> studentGrades;  // 学生的成绩列表
    private BigDecimal averageGrade;              // 学生的平均分
    private List<Integer> scoreDistribution;      // 分数分布
    private Integer totalEnrolledStudents;       // 总选课学生人数

    // Getters and Setters
    @Data
    public static class StudentGradeDTO {
        private Integer studentId;
        private String studentName;
        private BigDecimal grade;
        private Double attendanceRate; // 新增出勤率字段

        // 添加带参数的构造函数
        public StudentGradeDTO(Integer studentId, String studentName, BigDecimal grade, Double attendanceRate) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.grade = grade;
            this.attendanceRate = attendanceRate;
        }

        // Getters and Setters
    }
}
