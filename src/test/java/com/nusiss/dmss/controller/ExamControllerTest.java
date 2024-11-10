package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Exam;
import com.nusiss.dmss.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExamControllerTest {

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllExams() {
        // 准备数据
        when(examService.getAllExams()).thenReturn(Arrays.asList(new Exam(), new Exam()));

        // 执行操作
        ResponseEntity<ApiResponse<List<Exam>>> response = examController.getAllExams();

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Exams retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getExamById() {
        // 准备数据
        Integer id = 1;
        Exam exam = new Exam();
        exam.setExamId(id);
        when(examService.getExamById(id)).thenReturn(Optional.of(exam));

        // 执行操作
        ResponseEntity<ApiResponse<Exam>> response = examController.getExamById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Exam retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getExamId());
    }

    @Test
    void getExamById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(examService.getExamById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Exam>> response = examController.getExamById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Exam not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getExamsByCourseId_Success() {
        // 准备数据
        Integer courseId = 1;
        List<Exam> exams = Arrays.asList(new Exam(),new Exam());
        when(examService.getExamsByCourseId(courseId)).thenReturn(exams);

        // 执行操作
        ResponseEntity<ApiResponse<List<Exam>>> response = examController.getExamsByCourseId(courseId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Exams retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getExamsByCourseId_NotFound() {
        // 准备数据
        Integer courseId = 1;
        when(examService.getExamsByCourseId(courseId)).thenReturn(Collections.emptyList());

        // 执行操作
        ResponseEntity<ApiResponse<List<Exam>>> response = examController.getExamsByCourseId(courseId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Exams not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void createExam() {
        // 准备数据
        Exam exam = new Exam();
        exam.setExamId(1);
        when(examService.saveExam(exam)).thenReturn(exam);

        // 执行操作
        ResponseEntity<ApiResponse<Exam>> response = examController.createExam(exam);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Exam created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getExamId());
    }

    @Test
    void updateExam() {
        // 准备数据
        Integer id = 1;
        Exam updatedExam = new Exam();
        updatedExam.setExamId(id);
        when(examService.getExamById(id)).thenReturn(Optional.of(new Exam()));
        when(examService.saveExam(updatedExam)).thenReturn(updatedExam);

        // 执行操作
        ResponseEntity<ApiResponse<Exam>> response = examController.updateExam(id, updatedExam);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Exam updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getExamId());
    }

    @Test
    void updateExam_NotFound() {
        // 准备数据
        Integer id = 1;
        Exam updatedExam = new Exam();
        updatedExam.setExamId(id);
        when(examService.getExamById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Exam>> response = examController.updateExam(id, updatedExam);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Exam not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteExam() {
        // 准备数据
        Integer id = 1;
        when(examService.getExamById(id)).thenReturn(Optional.of(new Exam()));

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = examController.deleteExam(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Exam deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteExam_NotFound() {
        // 准备数据
        Integer id = 1;
        when(examService.getExamById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = examController.deleteExam(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Exam not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}
