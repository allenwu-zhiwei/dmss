package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.AttendanceRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateAttendanceStrategyTest {
    // 使用Mockito的@Mock注解模拟AttendanceRepository对象
    @Mock
    private AttendanceRepository attendanceRepository; // 被测试的业务逻辑类

    @InjectMocks// 使用@InjectMocks注解，自动注入attendanceRepository到CreateAttendanceStrategy对象
    private CreateAttendanceStrategy createAttendanceStrategy;

    @BeforeEach // 在每个测试方法执行前运行
    void setUp() {
        MockitoAnnotations.openMocks(this);// 初始化Mockito的模拟对象
    }

    @Test // 测试方法1:测试记录不存在
    void testCreateAttendanceRecord_Success() {
        // 准备测试数据
        Integer studentId = 1;  // 学生ID
        Integer courseId = 101;  // 课程ID
        AttendanceRecord newRecord = new AttendanceRecord();  // 创建一个新的出勤记录
        newRecord.setStudentId(studentId);  // 设置学生ID
        newRecord.setCourseId(courseId);  // 设置课程ID
        newRecord.setStatus("Present");  // 设置出勤状态为“Present”
        newRecord.setRemarks("On time");  // 设置备注为“准时”
        newRecord.setCreateUser("Teacher A");  // 设置创建记录的教师
        newRecord.setTeacherId(10);  // 设置教师ID
        // 设置Mock行为，当findAttendanceByStudentIdAndCourseId方法被调用时返回null，表示没有找到已有的记录
        when(attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId)).thenReturn(null);

        // 调用被测试方法，并确保不抛出异常
        assertDoesNotThrow(() -> createAttendanceStrategy.createAttendanceRecord(studentId, courseId, newRecord));

        // 验证保存逻辑，确保保存方法被调用一次
        verify(attendanceRepository, times(1)).save(newRecord);
    }

    @Test  // 测试方法2：出勤记录已经存在
    void testCreateAttendanceRecord_RecordAlreadyExists() {
        // 准备测试数据
        Integer studentId = 1;  // 学生ID
        Integer courseId = 101;  // 课程ID
        AttendanceRecord existingRecord = new AttendanceRecord();  // 创建一个已存在的出勤记录
        existingRecord.setStudentId(studentId);  // 设置学生ID
        existingRecord.setCourseId(courseId);  // 设置课程ID

        AttendanceRecord newRecord = new AttendanceRecord();  // 创建一个新的出勤记录
        newRecord.setStudentId(studentId);  // 设置学生ID
        newRecord.setCourseId(courseId);  // 设置课程ID
        newRecord.setStatus("Present");  // 设置出勤状态为“Present”
        newRecord.setRemarks("On time");  // 设置备注为“准时”
        newRecord.setCreateUser("Teacher A");  // 设置创建记录的教师
        newRecord.setTeacherId(10);  // 设置教师ID

        // 设置Mock行为，当findAttendanceByStudentIdAndCourseId方法被调用时返回已存在的记录
        when(attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId)).thenReturn(existingRecord);

        // 调用被测试方法并验证抛出异常，检查异常消息是否为“Attendance records already exist!”
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> createAttendanceStrategy.createAttendanceRecord(studentId, courseId, newRecord));
        assertEquals("Attendance records already exist!", exception.getMessage());  // 验证异常消息

        // 验证保存逻辑未被调用
        verify(attendanceRepository, never()).save(newRecord);
    }

    //测试创建出勤记录中其他不需要实现的方法
    @Test
    void testDeleteAttendanceRecord() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 调用被测试方法
        createAttendanceStrategy.deleteAttendanceRecord(studentId, courseId);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> createAttendanceStrategy.deleteAttendanceRecord(studentId, courseId));
    }
    @Test
    void testUpdateAttendanceRecord() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;
        AttendanceRecord updateRecord = new AttendanceRecord();
        updateRecord.setStudentId(studentId);
        updateRecord.setCourseId(courseId);
        updateRecord.setStatus("Present");

        // 调用被测试方法
        createAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> createAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updateRecord));
    }

    @Test
    void testFindAttendanceByStudentId() {
        // 准备测试数据
        Integer studentId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = createAttendanceStrategy.findAttendanceByStudentId(studentId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

    @Test
    void testFindAttendanceByTeacherId() {
        // 准备测试数据
        Integer teacherId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = createAttendanceStrategy.findAttendanceByTeacherId(teacherId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

}
