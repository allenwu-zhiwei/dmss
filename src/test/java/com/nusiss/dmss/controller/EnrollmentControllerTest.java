package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Enrollment;
import com.nusiss.dmss.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEnrollments() {
        // 准备数据
        when(enrollmentService.getAllEnrollments()).thenReturn(Arrays.asList(
                new Enrollment(),
                new Enrollment()
        ));

        // 执行操作
        ResponseEntity<ApiResponse<List<Enrollment>>> response = enrollmentController.getAllEnrollments();

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollments retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getEnrollmentById() {
        // 准备数据
        Integer id = 1;
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(id);
        when(enrollmentService.getEnrollmentById(id)).thenReturn(Optional.of(enrollment));

        // 执行操作
        ResponseEntity<ApiResponse<Enrollment>> response = enrollmentController.getEnrollmentById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollment retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getEnrollmentId());
    }

    @Test
    void getEnrollmentById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(enrollmentService.getEnrollmentById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Enrollment>> response = enrollmentController.getEnrollmentById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Enrollment not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getEnrollmentsByStudentId() {
        // 准备数据
        Integer studentId = 1;
        when(enrollmentService.getEnrollmentsByStudentId(studentId)).thenReturn(Arrays.asList(
                new Enrollment(),
                new Enrollment()
        ));

        // 执行操作
        ResponseEntity<ApiResponse<List<Enrollment>>> response = enrollmentController.getEnrollmentsByStudentrId(studentId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollments retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getEnrollmentsByCourseId() {
        // 准备数据
        Integer courseId = 1;
        when(enrollmentService.getEnrollmentsByCourseId(courseId)).thenReturn(Arrays.asList(
                new Enrollment(),
                new Enrollment()
        ));

        // 执行操作
        ResponseEntity<ApiResponse<List<Enrollment>>> response = enrollmentController.getEnrollmentsByCourseId(courseId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollments retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void createEnrollment() {
        // 准备数据
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(1);
        when(enrollmentService.saveEnrollment(enrollment)).thenReturn(enrollment);

        // 执行操作
        ResponseEntity<ApiResponse<Enrollment>> response = enrollmentController.createEnrollment(enrollment);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollment created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getEnrollmentId());
    }

    @Test
    void updateEnrollment() {
        // 准备数据
        Integer id = 1;
        Enrollment updatedEnrollment = new Enrollment();
        updatedEnrollment.setEnrollmentId(id);
        when(enrollmentService.getEnrollmentById(id)).thenReturn(Optional.of(new Enrollment()));
        when(enrollmentService.saveEnrollment(updatedEnrollment)).thenReturn(updatedEnrollment);

        // 执行操作
        ResponseEntity<ApiResponse<Enrollment>> response = enrollmentController.updateEnrollment(id, updatedEnrollment);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollment updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getEnrollmentId());
    }

    @Test
    void updateEnrollment_NotFound() {
        // 准备数据
        Integer id = 1;
        Enrollment updatedEnrollment = new Enrollment();
        updatedEnrollment.setEnrollmentId(id);
        when(enrollmentService.getEnrollmentById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Enrollment>> response = enrollmentController.updateEnrollment(id, updatedEnrollment);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Enrollment not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteEnrollment() {
        // 准备数据
        Integer id = 1;
        when(enrollmentService.getEnrollmentById(id)).thenReturn(Optional.of(new Enrollment()));

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = enrollmentController.deleteEnrollment(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Enrollment deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteEnrollment_NotFound() {
        // 准备数据
        Integer id = 1;
        when(enrollmentService.getEnrollmentById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = enrollmentController.deleteEnrollment(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Enrollment not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}