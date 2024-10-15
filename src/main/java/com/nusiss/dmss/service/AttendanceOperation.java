package com.nusiss.dmss.service;

//使用策略设计模式,定义策略接口

import com.nusiss.dmss.entity.AttendanceRecord;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface AttendanceOperation {
    //增加/创建出勤记录
    void createAttendanceRecord(Integer studentId, Integer courseId, AttendanceRecord newRecord);
    //删除出勤记录
    void deleteAttendanceRecord(Integer studentId, Integer courseId);
    //更新出勤记录
    void updateAttendanceRecord(Integer studentId, Integer courseId, AttendanceRecord updateRecord);
    //查找学生出勤记录
    List<AttendanceRecord>findAttendanceByStudentId(Integer studentId);
    //查找老师所教课程的所有出勤记录
    List<AttendanceRecord>findAttendanceByTeacherId(Integer teacherId);
}
