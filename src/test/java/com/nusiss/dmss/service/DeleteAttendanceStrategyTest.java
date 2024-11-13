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

class DeleteAttendanceStrategyTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @InjectMocks
    private DeleteAttendanceStrategy deleteAttendanceStrategy;
    // 初始化Mockito的模拟对象,Mockito通过反射机制来创建这些模拟对象，并且注入到测试类中。
    public DeleteAttendanceStrategyTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test   //测试成功删除已存在出勤记录的情况
    void testDeleteAttendanceRecord_Success() {
        // 设置 Mock 数据
        Integer studentId = 1;
        Integer courseId = 101;
        AttendanceRecord existingRecord = new AttendanceRecord();
        existingRecord.setStudentId(studentId);  // 设置学生ID
        existingRecord.setCourseId(courseId);  // 设置课程ID
        existingRecord.setAttendanceDate(LocalDateTime.of(2024, 11, 8, 10, 0));  // 设置出勤日期为2024年11月8日上午10点
        existingRecord.setStatus("Absent");  // 设置出勤状态为“Absent”
        existingRecord.setRemarks("Late");  //设置备注
        existingRecord.setUpdateDatetime(LocalDateTime.now());
        existingRecord.setUpdateUser("Teacher1");
        existingRecord.setTeacherId(1);

        // 设置Mock行为，当findAttendanceByStudentIdAndCourseId方法被调用时返回现有的记录
        when(attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId))
                .thenReturn(existingRecord);

        // 执行测试方法，并确保没有抛出异常
        assertDoesNotThrow(() -> deleteAttendanceStrategy.deleteAttendanceRecord(studentId, courseId));

        // 验证删除操作是否调用
        verify(attendanceRepository, times(1)).delete(existingRecord);  // 验证删除方法被调用一次
    }

    @Test  // 测试出勤记录不存在的情况
    void testDeleteAttendanceRecord_RecordNotFound() {
        // 设置 Mock 数据
        Integer studentId = 1;  // 学生ID
        Integer courseId = 101;  // 课程ID

        // 设置Mock行为，当findAttendanceByStudentIdAndCourseId方法被调用时返回null，表示没有找到记录
        when(attendanceRepository.findAttendanceByStudentIdAndCourseId(studentId, courseId))
                .thenReturn(null);

        // 执行测试并验证抛出异常，检查异常消息是否为“Attendance records don't exist!”
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> deleteAttendanceStrategy.deleteAttendanceRecord(studentId, courseId));
        assertEquals("Attendance records don't exist!", exception.getMessage());  // 验证异常消息

        // 验证删除方法没有被调用
        verify(attendanceRepository, never()).delete(any());  // 验证删除方法没有被调用
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
        deleteAttendanceStrategy.createAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> deleteAttendanceStrategy.createAttendanceRecord(studentId, courseId, updateRecord));
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
        deleteAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updateRecord);

        // 验证方法被调用（逻辑为空，只需验证无异常抛出）
        assertDoesNotThrow(() -> deleteAttendanceStrategy.updateAttendanceRecord(studentId, courseId, updateRecord));
    }

    @Test
    void testFindAttendanceByStudentId() {
        // 准备测试数据
        Integer studentId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = deleteAttendanceStrategy.findAttendanceByStudentId(studentId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

    @Test
    void testFindAttendanceByTeacherId() {
        // 准备测试数据
        Integer teacherId = 1;

        // 调用被测试方法
        List<AttendanceRecord> result = deleteAttendanceStrategy.findAttendanceByTeacherId(teacherId);

        // 验证返回值为 null（方法逻辑为直接返回 null）
        assertNull(result, "Expected result to be null");
    }

    @Test
    void testGetAttendanceRateByStudentIdAndCourseId_NotImplemented() {
        // 准备测试数据
        Integer studentId = 1;
        Integer courseId = 101;

        // 调用被测试方法
        Double result = deleteAttendanceStrategy.getAttendanceRateByStudentIdAndCourseId(studentId, courseId);

        // 验证返回值为默认值 0.0
        assertEquals(0.0, result, "Expected attendance rate to be 0.0 as the method is not implemented");

        // 验证Repository方法未被调用
        verifyNoInteractions(attendanceRepository);
    }

}
