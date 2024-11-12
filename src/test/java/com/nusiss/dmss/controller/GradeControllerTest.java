package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Grade;
import com.nusiss.dmss.service.GradeService;
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
import static org.mockito.Mockito.*;

class GradeControllerTest {

    @Mock
    private GradeService gradeService;

    @InjectMocks
    private GradeController gradeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGrades() {
        // 准备数据
        when(gradeService.getAllGrades()).thenReturn(Arrays.asList(new Grade(), new Grade()));

        // 执行操作
        ResponseEntity<ApiResponse<List<Grade>>> response = gradeController.getAllGrades();

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grades retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getGradeById_Found() {
        // 准备数据
        Integer id = 1;
        Grade grade = new Grade();
        grade.setGradeId(id);
        when(gradeService.getGradeById(id)).thenReturn(Optional.of(grade));

        // 执行操作
        ResponseEntity<ApiResponse<Grade>> response = gradeController.getGradeById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grade retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getGradeId());
    }

    @Test
    void getGradeById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(gradeService.getGradeById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Grade>> response = gradeController.getGradeById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Grade not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getGradeByStudentId_Found() {
        // 准备数据
        Integer studentId = 1;
        List<Grade> grades = Arrays.asList(new Grade(), new Grade());
        when(gradeService.getGradeByStudentId(studentId)).thenReturn(grades);

        // 执行操作
        ResponseEntity<ApiResponse<List<Grade>>> response = gradeController.getGradeByStudentId(studentId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grades retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getGradeByStudentId_NotFound() {
        // 准备数据
        Integer studentId = 1;
        when(gradeService.getGradeByStudentId(studentId)).thenReturn(Collections.emptyList());

        // 执行操作
        ResponseEntity<ApiResponse<List<Grade>>> response = gradeController.getGradeByStudentId(studentId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Grades not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getGradeByCourseId_Found() {
        // 准备数据
        Integer courseId = 1;
        List<Grade> grades = Arrays.asList(new Grade(), new Grade());
        when(gradeService.getGradeByCourseId(courseId)).thenReturn(grades);

        // 执行操作
        ResponseEntity<ApiResponse<List<Grade>>> response = gradeController.getGradeByCourseId(courseId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grades retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getGradeByCourseId_NotFound() {
        // 准备数据
        Integer courseId = 1;
        when(gradeService.getGradeByCourseId(courseId)).thenReturn(Collections.emptyList());

        // 执行操作
        ResponseEntity<ApiResponse<List<Grade>>> response = gradeController.getGradeByCourseId(courseId);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Grades not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void createGrade() {
        // 准备数据
        Grade grade = new Grade();
        grade.setGradeId(1);
        when(gradeService.saveGrade(any(Grade.class))).thenReturn(grade);

        // 执行操作
        ResponseEntity<ApiResponse<Grade>> response = gradeController.createGrade(grade);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grade created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getGradeId());
    }

    @Test
    void updateGrade_Found() {
        // 准备数据
        Integer id = 1;
        Grade updatedGrade = new Grade();
        updatedGrade.setGradeId(id);
        when(gradeService.getGradeById(id)).thenReturn(Optional.of(new Grade()));
        when(gradeService.saveGrade(any(Grade.class))).thenReturn(updatedGrade);

        // 执行操作
        ResponseEntity<ApiResponse<Grade>> response = gradeController.updateGrade(id, updatedGrade);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grade updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getGradeId());
    }

    @Test
    void updateGrade_NotFound() {
        // 准备数据
        Integer id = 1;
        Grade updatedGrade = new Grade();
        updatedGrade.setGradeId(id);
        when(gradeService.getGradeById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Grade>> response = gradeController.updateGrade(id, updatedGrade);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Grade not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteGrade_Found() {
        // 准备数据
        Integer id = 1;
        when(gradeService.getGradeById(id)).thenReturn(Optional.of(new Grade()));
        doNothing().when(gradeService).deleteGrade(id);

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = gradeController.deleteGrade(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Grade deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteGrade_NotFound() {
        // 准备数据
        Integer id = 1;
        when(gradeService.getGradeById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = gradeController.deleteGrade(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Grade not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }
}
