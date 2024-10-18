package com.nusiss.dmss.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // 实体类
@Table(name = "Attendance") //映射到数据库表"Attendance"
@Getter
@Setter
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //使用数据库的自增特性来生成主键值,插入新的值，自动生成新的自增整数值
    private Integer attendanceId; //出勤记录ID编号（主键）（整数值）

    //学生ID不为空
    @Column(name = "student_id")   //该列在数据库中不能为 NULL,需为有效值
    private Integer studentId;  //学生ID（整数值）

    @Column(name = "course_id")
    private Integer courseId;    //课程ID

    @Column(name = "attendance_date")
    private LocalDateTime attendanceDate;   //出勤日期（发生的具体的日期时间）

    @Column(name = "status")
    private String status;  //出勤状态，如 "Present", "Absent", "Late"，

    @Column(name = "remarks")
    private String remarks;  // 备注信息（可选）,可选则可以为空

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;   // 创建出勤记录的时间，

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;   //更新出勤记录的时间

    @Column(name = "create_user")
    private String createUser;  // 创建出勤记录的教师是哪位

    @Column(name = "update_user")
    private String updateUser;  // 更新出勤记录的教师是哪位

    @Column(name = "teacher_id")
    private Integer teacherId;  // 教师ID

}
