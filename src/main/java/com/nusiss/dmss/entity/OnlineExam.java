package com.nusiss.dmss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
//@Table(name = "online_exams")
public class OnlineExam extends Exam {

    private static final String DEFAULT_TYPE = "ONLINE";

    @Column(name = "platform_url", length = 255, nullable = false)
    private String platformUrl; // 在线考试的平台 URL

    @Column(name = "access_code", length = 100)
    private String accessCode; // 考试访问码

    public OnlineExam() {
        this.setExamType(DEFAULT_TYPE); // 设置默认类型为 ONLINE
    }

    // 构造方法
    public OnlineExam(Integer courseId, String examName, LocalDate examDate, Integer durationMinutes, String platformUrl, String accessCode) {
        super();
        this.setCourseId(courseId);
        this.setExamName(examName);
        this.setExamDate(examDate);
        this.setDurationMinutes(durationMinutes);
        this.setExamType(DEFAULT_TYPE);
        this.platformUrl = platformUrl;
        this.accessCode = accessCode;
    }

    // Getter and Setter for platformUrl
    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    // Getter and Setter for accessCode
    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    @Override
    public String toString() {
        return "OnlineExam{" +
                "examId=" + this.getExamId() +
                ", courseId=" + this.getCourseId() +
                ", examName='" + this.getExamName() + '\'' +
                ", examDate=" + this.getExamDate() +
                ", examType='" + this.getExamType() + '\'' +
                ", durationMinutes=" + this.getDurationMinutes() +
                ", location='" + this.getLocation() + '\'' + // 可选字段
                ", status='" + this.getStatus() + '\'' +
                ", createDatetime=" + this.getCreateDatetime() +
                ", updateDatetime=" + this.getUpdateDatetime() +
                ", platformUrl='" + platformUrl + '\'' +
                ", accessCode='" + accessCode + '\'' +
                '}';
    }
}
