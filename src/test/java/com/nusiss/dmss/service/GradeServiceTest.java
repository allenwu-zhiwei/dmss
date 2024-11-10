package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.GradeRepository;
import com.nusiss.dmss.entity.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGrades() {
        // 准备数据
        when(gradeRepository.findAll()).thenReturn(Arrays.asList(new Grade(), new Grade()));

        // 执行操作
        List<Grade> grades = gradeService.getAllGrades();

        // 验证结果
        assertNotNull(grades);
        assertEquals(2, grades.size());
    }

    @Test
    void getGradeById_Found() {
        // 准备数据
        Integer id = 1;
        Grade grade = new Grade();
        grade.setGradeId(id);
        when(gradeRepository.findById(id)).thenReturn(Optional.of(grade));

        // 执行操作
        Optional<Grade> result = gradeService.getGradeById(id);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getGradeId());
    }

    @Test
    void getGradeById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(gradeRepository.findById(id)).thenReturn(Optional.empty());

        // 执行操作
        Optional<Grade> result = gradeService.getGradeById(id);

        // 验证结果
        assertFalse(result.isPresent());
    }

    @Test
    void getGradeByStudentId_Found() {
        // 准备数据
        Integer studentId = 1;
        List<Grade> grades = Arrays.asList(new Grade(), new Grade());
        when(gradeRepository.findByStudentId(studentId)).thenReturn(grades);

        // 执行操作
        List<Grade> result = gradeService.getGradeByStudentId(studentId);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getGradeByStudentId_NotFound() {
        // 准备数据
        Integer studentId = 1;
        when(gradeRepository.findByStudentId(studentId)).thenReturn(Collections.emptyList());

        // 执行操作
        List<Grade> result = gradeService.getGradeByStudentId(studentId);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getGradeByCourseId_Found() {
        // 准备数据
        Integer courseId = 1;
        List<Grade> grades = Arrays.asList(new Grade(), new Grade());
        when(gradeRepository.findByCourseId(courseId)).thenReturn(grades);

        // 执行操作
        List<Grade> result = gradeService.getGradeByCourseId(courseId);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getGradeByCourseId_NotFound() {
        // 准备数据
        Integer courseId = 1;
        when(gradeRepository.findByCourseId(courseId)).thenReturn(Collections.emptyList());

        // 执行操作
        List<Grade> result = gradeService.getGradeByCourseId(courseId);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void saveGrade() {
        // 准备数据
        Grade grade = new Grade();
        grade.setGradeId(1);
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        // 执行操作
        Grade savedGrade = gradeService.saveGrade(grade);

        // 验证结果
        assertNotNull(savedGrade);
        assertEquals(1, savedGrade.getGradeId());
    }

    @Test
    void deleteGrade() {
        // 准备数据
        Integer id = 1;
        doNothing().when(gradeRepository).deleteById(id);

        // 执行操作
        gradeService.deleteGrade(id);

        // 验证删除操作
        verify(gradeRepository, times(1)).deleteById(id);
    }
}
