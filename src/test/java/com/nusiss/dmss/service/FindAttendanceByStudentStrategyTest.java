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

class FindAttendanceByStudentStrategyTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private FindAttendanceByStudentStrategy findAttendanceByStudentStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //测试成功返回记录的情况
    @Test
    void testFindAttendanceByStudentId_Success() {
        // 设置 Mock 数据
        Integer studentId = 1;  // 学生ID
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();  // 创建一个空的出勤记录列表
        AttendanceRecord record1 = new AttendanceRecord();  // 创建第一条出勤记录
        record1.setStudentId(studentId);  // 设置学生ID
        record1.setCourseId(101);  // 设置课程ID
        AttendanceRecord record2 = new AttendanceRecord();  // 创建第二条出勤记录
        record2.setStudentId(studentId);  // 设置学生ID
        record2.setCourseId(102);  // 设置课程ID
        attendanceRecords.add(record1);  // 将第一条记录加入到列表
        attendanceRecords.add(record2);  // 将第二条记录加入到列表

        // 设置Mock行为，当findAttendanceByStudentId方法被调用时返回上面设置的attendanceRecords列表
        when(attendanceRepository.findAttendanceByStudentId(studentId)).thenReturn(attendanceRecords);

        // 执行测试方法
        List<AttendanceRecord> result = findAttendanceByStudentStrategy.findAttendanceByStudentId(studentId);

        // 验证返回结果
        assertNotNull(result);  // 确保返回的结果不为null
        assertEquals(2, result.size());  // 确保返回的记录数为2
        assertEquals(attendanceRecords, result);  // 确保返回的记录与预期的记录列表一致

        // 验证调用
        verify(attendanceRepository, times(1)).findAttendanceByStudentId(studentId);  // 验证findAttendanceByStudentId方法被调用一次
    }

    //测试无记录的情况
    @Test
    void testFindAttendanceByStudentId_NoRecordsFound() {
        // 设置 Mock 数据
        Integer studentId = 1;  // 学生ID

        // 设置Mock行为，当findAttendanceByStudentId方法被调用时返回一个空列表
        when(attendanceRepository.findAttendanceByStudentId(studentId)).thenReturn(new ArrayList<>());

        // 执行测试方法
        List<AttendanceRecord> result = findAttendanceByStudentStrategy.findAttendanceByStudentId(studentId);

        // 验证返回结果
        assertNotNull(result);  // 确保返回的结果不为null
        assertTrue(result.isEmpty());  // 确保返回的记录是空的

        // 验证调用
        verify(attendanceRepository, times(1)).findAttendanceByStudentId(studentId);  // 验证findAttendanceByStudentId方法被调用一次
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
        findAttendanceByStudentStrategy.createAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> findAttendanceByStudentStrategy.createAttendanceRecord(studentId, courseId, updateRecord));
    }

    @Test
    void testDeleteAttendanceRecord() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 调用被测试方法
        findAttendanceByStudentStrategy.deleteAttendanceRecord(studentId, courseId);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> findAttendanceByStudentStrategy.deleteAttendanceRecord(studentId, courseId));
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
        findAttendanceByStudentStrategy.updateAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> findAttendanceByStudentStrategy.updateAttendanceRecord(studentId, courseId, updateRecord));
    }

    @Test
    void testFindAttendanceByTeacherId() {
        // 准备测试数据
        Integer teacherId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = findAttendanceByStudentStrategy.findAttendanceByTeacherId(teacherId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

    @Test
    void testGetAttendanceRate_NoRecords() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 模拟Repository返回没有记录的情况
        when(attendanceRepository.countByStudentIdAndCourseId(studentId, courseId)).thenReturn(0);

        // 调用被测试方法
        Double result = findAttendanceByStudentStrategy.getAttendanceRateByStudentIdAndCourseId(studentId, courseId);

        // 验证返回值为 0.0
        assertEquals(0.0, result, "Expected attendance rate to be 0.0 when there are no records");
    }

    @Test
    void testGetAttendanceRate_PartialAttendance() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 模拟Repository返回部分出勤记录的情况
        when(attendanceRepository.countByStudentIdAndCourseId(studentId, courseId)).thenReturn(10); // 总记录数
        when(attendanceRepository.countByStudentIdAndCourseIdAndStatus(studentId, courseId, "Present")).thenReturn(6); // 出勤记录数

        // 调用被测试方法
        Double result = findAttendanceByStudentStrategy.getAttendanceRateByStudentIdAndCourseId(studentId, courseId);

        // 验证返回值为正确的出勤率
        assertEquals(60.0, result, "Expected attendance rate to be 60.0 when 6 out of 10 records are 'Present'");
    }

    @Test
    void testGetAttendanceRate_FullAttendance() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 模拟Repository返回全勤记录的情况
        when(attendanceRepository.countByStudentIdAndCourseId(studentId, courseId)).thenReturn(10); // 总记录数
        when(attendanceRepository.countByStudentIdAndCourseIdAndStatus(studentId, courseId, "Present")).thenReturn(10); // 出勤记录数

        // 调用被测试方法
        Double result = findAttendanceByStudentStrategy.getAttendanceRateByStudentIdAndCourseId(studentId, courseId);

        // 验证返回值为 100.0
        assertEquals(100.0, result, "Expected attendance rate to be 100.0 when all 10 records are 'Present'");
    }


}
