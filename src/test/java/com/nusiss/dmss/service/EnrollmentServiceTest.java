package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.EnrollmentRepository;
import com.nusiss.dmss.entity.Enrollment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEnrollments() {
        // 准备数据
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(
                new Enrollment(),
                new Enrollment()
        ));

        // 执行操作
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        // 验证结果
        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
    }

    @Test
    void getEnrollmentById() {
        // 准备数据
        Integer id = 1;
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(id);
        when(enrollmentRepository.findById(id)).thenReturn(Optional.of(enrollment));

        // 执行操作
        Optional<Enrollment> result = enrollmentService.getEnrollmentById(id);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getEnrollmentId());
    }

    @Test
    void getEnrollmentsByStudentId() {
        // 准备数据
        Integer studentId = 1;
        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(Arrays.asList(
                new Enrollment(),
                new Enrollment()
        ));

        // 执行操作
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);

        // 验证结果
        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
    }

    @Test
    void getEnrollmentsByCourseId() {
        // 准备数据
        Integer courseId = 1;
        when(enrollmentRepository.findByCourseId(courseId)).thenReturn(Arrays.asList(
                new Enrollment(),
                new Enrollment()
        ));

        // 执行操作
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);

        // 验证结果
        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
    }

    @Test
    void saveEnrollment() {
        // 准备数据
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(1);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        // 执行操作
        Enrollment savedEnrollment = enrollmentService.saveEnrollment(enrollment);

        // 验证结果
        assertNotNull(savedEnrollment);
        assertEquals(1, savedEnrollment.getEnrollmentId());
    }

    @Test
    void deleteEnrollment() {
        // 准备数据
        Integer id = 1;

        // 执行操作
        enrollmentService.deleteEnrollment(id);

        // 验证操作
        verify(enrollmentRepository, times(1)).deleteById(id);
    }
}