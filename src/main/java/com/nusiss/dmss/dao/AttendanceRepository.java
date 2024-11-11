package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//定义一个接口AttendanceRepository用于操作AttendanceRecord实体类
//继承于JpaRepository接口，该接口提供了对数据库操作的常用方法（CRUD）
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {
    //通过学生ID去查询学生的出勤记录,返回一个List集合
    @Query("SELECT a FROM AttendanceRecord a WHERE a.studentId = :studentId")
    List<AttendanceRecord> findAttendanceByStudentId(Integer studentId);
    // 根据老师ID查找他所教课程的所有学生的出勤记录
    @Query("SELECT a FROM AttendanceRecord a WHERE a.teacherId = :teacherId")
    List<AttendanceRecord> findAttendanceByTeacherId(Integer teacherId);
    //新增出勤记录不需要在repository添加方法，JPA自带了save方法
    //更新出勤记录也是不需要在repository添加方法，JPA也提供了save方法，直接调用即可
    //在更新出勤记录之前先查看该学生是否有出勤记录
    // 通过 studentId 和 courseId 查询该学生是否有出勤记录
    // 根据 studentId 和 courseId 删除出勤记录
    @Query("SELECT a FROM AttendanceRecord a WHERE a.studentId = :studentId AND a.courseId = :courseId")
    AttendanceRecord findAttendanceByStudentIdAndCourseId(Integer studentId, Integer courseId);

    // 统计某个学生在某门课程的总出勤记录数
    int countByStudentIdAndCourseId(Integer studentId, Integer courseId);

    // 统计某个学生在某门课程的出勤记录数（status 为 "Present"）
    int countByStudentIdAndCourseIdAndStatus(Integer studentId, Integer courseId, String status);
}
