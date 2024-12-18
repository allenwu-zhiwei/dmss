package com.nusiss.dmss.service;


import com.nusiss.dmss.dao.AttendanceRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FindAttendanceByTeacherStrategy implements AttendanceOperation {

    @Autowired
    AttendanceRepository attendanceRepository;
    //查找老师所教课程的所有出勤记录
    @Override
    public List<AttendanceRecord> findAttendanceByTeacherId(Integer teacherId) {
        return attendanceRepository.findAttendanceByTeacherId(teacherId);
    }

    @Override
    public Double getAttendanceRateByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        return 0.0;
    }

    //增加/创建出勤记录
    @Override
    public void createAttendanceRecord(Integer studentId, Integer courseId, AttendanceRecord newRecord) {
        //此策略不执行
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


}
