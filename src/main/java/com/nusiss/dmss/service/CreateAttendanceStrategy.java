package com.nusiss.dmss.service;


import com.nusiss.dmss.dao.AttendanceRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Component("createAttendanceStrategy")
@Service
public class CreateAttendanceStrategy implements AttendanceOperation {
    @Autowired
    private AttendanceRepository attendanceRepository;  // 注入AttendanceRepository对象

    //增加/创建出勤记录
    @Override
    public void createAttendanceRecord(Integer studentId, Integer courseId, AttendanceRecord newRecord) {
        // 首先根据studentId和courseId查找是否已经存在出勤记录
        AttendanceRecord existingRecord = attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId);

        if (existingRecord == null) {
            // 如果不存在出勤记录，则创建新的记录
            newRecord.setStudentId(newRecord.getStudentId());   // 设置学生ID
            newRecord.setCourseId(newRecord.getCourseId());     // 设置课程ID
            newRecord.setAttendanceDate(LocalDateTime.now());   // 设置出勤时间为当前时间
            newRecord.setStatus(newRecord.getStatus());         // 设置出勤状态
            newRecord.setRemarks(newRecord.getRemarks());       // 设置备注
            newRecord.setCreateDatetime(LocalDateTime.now());   // 设置创建时间为当前时间
            newRecord.setCreateUser(newRecord.getCreateUser()); // 设置创建者信息
            newRecord.setTeacherId(newRecord.getTeacherId());   // 设置教师信息
            attendanceRepository.save(newRecord);  // 保存记录

        }
        //如果已经存在出勤记录，则抛出异常"出勤记录已存在"
        else {
            throw new RuntimeException("Attendance records already exist!");
        }
    }

    //删除出勤记录
    @Override
    public void deleteAttendanceRecord(Integer studentId, Integer courseId){
        //此策略不执行
    }

    //更新出勤记录
    @Override
    public void updateAttendanceRecord(Integer studentId, Integer courseId, AttendanceRecord updateRecord) {
        //此策略不执行
    }

    //查找学生出勤记录
    @Override
    public List<AttendanceRecord> findAttendanceByStudentId(Integer studentId) {
        return null;//此策略不执行
    }

    //查找老师所教课程的所有出勤记录
    @Override
    public List<AttendanceRecord> findAttendanceByTeacherId(Integer teacherId) {
        return null;//此策略不执行
    }
}
