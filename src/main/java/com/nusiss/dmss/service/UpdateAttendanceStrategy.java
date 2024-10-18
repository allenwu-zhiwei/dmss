package com.nusiss.dmss.service;


import com.nusiss.dmss.dao.AttendanceRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateAttendanceStrategy implements AttendanceOperation{
    @Autowired
    AttendanceRepository attendanceRepository;
    //更新出勤记录
    @Override
    public void updateAttendanceRecord(Integer studentId, Integer courseId, AttendanceRecord updatedRecord) {
        // 首先根据studentId和courseId查找是否存在记录
        AttendanceRecord existingRecord = attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId);

        if (existingRecord != null) {
            existingRecord.setAttendanceDate(updatedRecord.getAttendanceDate());  // 更新出勤日期
            existingRecord.setStatus(updatedRecord.getStatus());          // 更新出勤状态
            existingRecord.setRemarks(updatedRecord.getRemarks());        // 更新备注
            existingRecord.setUpdateDatetime(LocalDateTime.now());        // 更新为当前时间
            existingRecord.setUpdateUser(updatedRecord.getUpdateUser());  // 更新更新者信息
            existingRecord.setTeacherId(updatedRecord.getTeacherId());    // 更新教师ID
            attendanceRepository.save(existingRecord);
        } else {
            throw new RuntimeException("出勤记录不存在");
        }
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
