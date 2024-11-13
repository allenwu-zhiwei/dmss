package com.nusiss.dmss.controller;


import com.nusiss.dmss.entity.AttendanceRecord;
import com.nusiss.dmss.service.AttendanceOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    // 创建出勤记录
    @Autowired
    private AttendanceOperation createAttendanceStrategy;  // 新增出勤记录策略

    @PostMapping("/add/{studentId}/{courseId}")
    public ResponseEntity<String> createAttendanceRecord(@PathVariable Integer studentId,
                                                         @PathVariable Integer courseId,
                                                         @RequestBody AttendanceRecord newRecord) {
        try {
            // 调用 createAttendanceRecord 方法
            createAttendanceStrategy.createAttendanceRecord(studentId, courseId, newRecord);
            return ResponseEntity.ok("Attendance record added successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //删除操作实现
    @Autowired
    private AttendanceOperation deleteAttendanceStrategy;  // 删除出勤记录策略
    @DeleteMapping("/delete/{studentId}/{courseId}")
    public String deleteAttendanceRecord(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        // 调用删除策略删除出勤记录
        deleteAttendanceStrategy.deleteAttendanceRecord(studentId, courseId);
        return "Attendance record deleted successfully!";
    }

    //更新操作实现
    @Autowired
    private AttendanceOperation updateAttendanceStrategy; // 更新出勤记录策略
    @PutMapping("/update/{studentId}/{courseId}")
    public String updateAttendanceRecord(@PathVariable Integer studentId,
                                         @PathVariable Integer courseId,
                                         @RequestBody AttendanceRecord updateRecord){
        // 调用策略更新出勤记录
        updateAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updateRecord);
        return "Attendance record updated successfully!";
    }

    //查找操作实现
    @Autowired
    private AttendanceOperation findAttendanceByStudentStrategy; // 查找学生出勤策略
    @Autowired
    private AttendanceOperation findAttendanceByTeacherStrategy; // 查找老师所教学生出勤策略

    // 查找学生出勤记录
    @GetMapping("/student/{studentId}")
    public List<AttendanceRecord>findAttendanceByStudent(@PathVariable Integer studentId){
        // 使用学生ID查找出勤记录
        return findAttendanceByStudentStrategy.findAttendanceByStudentId(studentId);
    }
    // 查找老师所教课程的所有学生出勤记录
    @GetMapping("/teacher/{teacherId}")
    public List<AttendanceRecord>findAttendanceByTeacher(@PathVariable Integer teacherId){
        // 使用老师ID查找出勤记录
        return findAttendanceByTeacherStrategy.findAttendanceByTeacherId(teacherId);
    }

}
