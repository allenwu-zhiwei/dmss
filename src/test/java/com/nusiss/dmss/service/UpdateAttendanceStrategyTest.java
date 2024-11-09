package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.AttendanceRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateAttendanceStrategyTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private UpdateAttendanceStrategy updateAttendanceStrategy;

    public UpdateAttendanceStrategyTest() {
        MockitoAnnotations.openMocks(this);
    }

    //测试成功更新已存在出勤记录的情况
    @Test
    void testUpdateAttendanceRecord_Success() {
        // 设置 Mock 数据
        Integer studentId = 1;  // 学生ID
        Integer courseId = 101;  // 课程ID
        AttendanceRecord existingRecord = new AttendanceRecord();  // 创建一个现有的出勤记录
        existingRecord.setStudentId(studentId);  // 设置学生ID
        existingRecord.setCourseId(courseId);  // 设置课程ID
        existingRecord.setAttendanceDate(LocalDateTime.of(2024, 11, 8, 10, 0));  // 设置出勤日期为2024年11月8日上午10点
        existingRecord.setStatus("Absent");  // 设置出勤状态为“Absent”
        existingRecord.setRemarks("Late");  //设置备注
        existingRecord.setUpdateDatetime(LocalDateTime.of(2024, 11, 8, 10, 0));
        existingRecord.setUpdateUser("Teacher1");
        existingRecord.setTeacherId(1);

        AttendanceRecord updatedRecord = new AttendanceRecord();  // 创建一个更新后的出勤记录
        updatedRecord.setStudentId(studentId);  // 设置学生ID
        updatedRecord.setCourseId(courseId);  // 设置课程ID
        updatedRecord.setAttendanceDate(LocalDateTime.of(2024, 11, 8, 12, 0));  // 设置更新后的出勤日期为2024年11月8日下午12点
        updatedRecord.setStatus("Present");  // 设置更新后的出勤状态为“Present”
        updatedRecord.setRemarks("OnTime");  //设置备注
        updatedRecord.setUpdateDatetime(LocalDateTime.of(2024, 11, 8, 12, 0));
        updatedRecord.setUpdateUser("Teacher2");
        updatedRecord.setTeacherId(2);

        // 设置Mock行为，当findAttendanceByStudentIdAndCourseId方法被调用时返回现有的记录
        when(attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId))
                .thenReturn(existingRecord);

        // 执行测试方法，并确保没有抛出异常
        assertDoesNotThrow(() -> updateAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updatedRecord));

        // 验证更新后的记录
        assertEquals("Present", existingRecord.getStatus());  // 确保状态被更新为“Present”
        // 使用固定时间进行断言
        assertEquals(LocalDateTime.of(2024, 11, 8, 12, 0), existingRecord.getAttendanceDate());  // 确保出勤时间被更新为12点
        assertEquals("OnTime",existingRecord.getRemarks());
        assertEquals(LocalDateTime.of(2024, 11, 8, 12, 0), existingRecord.getUpdateDatetime());
        // 使用固定时间进行断言
        assertEquals(LocalDateTime.of(2024, 11, 8, 12, 0), existingRecord.getUpdateDatetime());
        assertEquals("Teacher2",existingRecord.getUpdateUser());
        assertEquals(2,existingRecord.getTeacherId());
        verify(attendanceRepository, times(1)).save(existingRecord);  // 验证保存方法被调用一次
    }

    @Test   // 测试出勤记录不存在的情况
    void testUpdateAttendanceRecord_RecordNotFound() {
        // 设置 Mock 数据
        Integer studentId = 1;  // 学生ID
        Integer courseId = 101;  // 课程ID
        AttendanceRecord updatedRecord = new AttendanceRecord();  // 创建一个更新的出勤记录
        updatedRecord.setStudentId(studentId);  // 设置学生ID
        updatedRecord.setCourseId(courseId);  // 设置课程ID

        // 设置Mock行为，当findAttendanceByStudentIdAndCourseId方法被调用时返回null，表示没有找到记录
        when(attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId))
                .thenReturn(null);

        // 执行测试并验证抛出异常，检查异常消息是否为“Attendance records do not exist!”
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> updateAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updatedRecord));
        assertEquals("Attendance records do not exist!", exception.getMessage());  // 验证异常消息

        // 验证保存方法未被调用
        verify(attendanceRepository, never()).save(any());  // 验证保存方法没有被调用
    }

    //测试创建出勤记录中其他不需要实现的方法
    @Test
    void testDeleteAttendanceRecord() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 调用被测试方法
        updateAttendanceStrategy.deleteAttendanceRecord(studentId, courseId);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> updateAttendanceStrategy.deleteAttendanceRecord(studentId, courseId));
    }
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
        updateAttendanceStrategy.createAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> updateAttendanceStrategy.createAttendanceRecord(studentId, courseId, updateRecord));
    }

    @Test
    void testFindAttendanceByStudentId() {
        // 准备测试数据
        Integer studentId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = updateAttendanceStrategy.findAttendanceByStudentId(studentId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

    @Test
    void testFindAttendanceByTeacherId() {
        // 准备测试数据
        Integer teacherId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = updateAttendanceStrategy.findAttendanceByTeacherId(teacherId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }
}
