package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.AttendanceRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindAttendanceByTeacherStrategyTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private FindAttendanceByTeacherStrategy findAttendanceByTeacherStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // 测试成功返回记录的情况
    @Test
    void testFindAttendanceByTeacherId_Success() {
        // 设置 Mock 数据
        Integer teacherId = 101;  // 教师ID
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();  // 创建一个空的出勤记录列表
        AttendanceRecord record1 = new AttendanceRecord();  // 创建第一条出勤记录
        record1.setTeacherId(teacherId);  // 设置教师ID
        record1.setStudentId(1);  // 设置学生ID
        record1.setCourseId(201);  // 设置课程ID
        AttendanceRecord record2 = new AttendanceRecord();  // 创建第二条出勤记录
        record2.setTeacherId(teacherId);  // 设置教师ID
        record2.setStudentId(2);  // 设置学生ID
        record2.setCourseId(202);  // 设置课程ID
        attendanceRecords.add(record1);  // 将第一条记录加入到列表
        attendanceRecords.add(record2);  // 将第二条记录加入到列表

        // 设置Mock行为，当findAttendanceByTeacherId方法被调用时返回上面设置的attendanceRecords列表
        when(attendanceRepository.findAttendanceByTeacherId(teacherId)).thenReturn(attendanceRecords);

        // 执行测试方法
        List<AttendanceRecord> result = findAttendanceByTeacherStrategy.findAttendanceByTeacherId(teacherId);

        // 验证返回结果
        assertNotNull(result);  // 确保返回的结果不为null
        assertEquals(2, result.size());  // 确保返回的记录数为2
        assertEquals(attendanceRecords, result);  // 确保返回的记录与预期的记录列表一致

        // 验证调用
        verify(attendanceRepository, times(1)).findAttendanceByTeacherId(teacherId);  // 验证findAttendanceByTeacherId方法被调用一次
    }
    // 测试无记录的情况
    @Test
    void testFindAttendanceByTeacherId_NoRecordsFound() {
        // 设置 Mock 数据
        Integer teacherId = 101;  // 教师ID

        // 设置Mock行为，当findAttendanceByTeacherId方法被调用时返回一个空列表
        when(attendanceRepository.findAttendanceByTeacherId(teacherId)).thenReturn(new ArrayList<>());

        // 执行测试方法
        List<AttendanceRecord> result = findAttendanceByTeacherStrategy.findAttendanceByTeacherId(teacherId);

        // 验证返回结果
        assertNotNull(result);  // 确保返回的结果不为null
        assertTrue(result.isEmpty());  // 确保返回的记录是空的

        // 验证调用
        verify(attendanceRepository, times(1)).findAttendanceByTeacherId(teacherId);  // 验证findAttendanceByTeacherId方法被调用一次
    }

    //测试创建出勤记录中其他不需要实现的方法
    @Test
    void testCreateAttendanceRecord() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;
        AttendanceRecord updateRecord = new AttendanceRecord();
        updateRecord.setStudentId(studentId);
        updateRecord.setCourseId(courseId);
        updateRecord.setStatus("Present");

        // 调用被测试方法
        findAttendanceByTeacherStrategy.createAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> findAttendanceByTeacherStrategy.createAttendanceRecord(studentId, courseId, updateRecord));
    }
    
    @Test
    void testDeleteAttendanceRecord() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 调用被测试方法
        findAttendanceByTeacherStrategy.deleteAttendanceRecord(studentId, courseId);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> findAttendanceByTeacherStrategy.deleteAttendanceRecord(studentId, courseId));
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
        findAttendanceByTeacherStrategy.updateAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> findAttendanceByTeacherStrategy.updateAttendanceRecord(studentId, courseId, updateRecord));
    }

    @Test
    void testFindAttendanceByStudentId() {
        // 准备测试数据
        Integer studentId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = findAttendanceByTeacherStrategy.findAttendanceByStudentId(studentId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

    @Test
    void testGetAttendanceRateByStudentIdAndCourseId_NotImplemented() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 调用被测试方法
        Double result = findAttendanceByTeacherStrategy.getAttendanceRateByStudentIdAndCourseId(studentId, courseId);

        // 验证返回值为默认值 0.0
        assertEquals(0.0, result, "Expected attendance rate to be 0.0 as the method is not implemented");

        // 验证Repository方法未被调用
        verifyNoInteractions(attendanceRepository);
    }

}
