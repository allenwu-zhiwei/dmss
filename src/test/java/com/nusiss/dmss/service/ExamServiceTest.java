package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.ExamRepository;
import com.nusiss.dmss.entity.Exam;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private ExamServiceImpl examService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllExams() {
        // 准备数据
        when(examRepository.findAll()).thenReturn(Arrays.asList(new Exam(), new Exam()));

        // 执行操作
        List<Exam> exams = examService.getAllExams();

        // 验证结果
        assertNotNull(exams);
        assertEquals(2, exams.size());
    }

    @Test
    void getExamById_Found() {
        // 准备数据
        Integer id = 1;
        Exam exam = new Exam();
        exam.setExamId(id);
        when(examRepository.findById(id)).thenReturn(Optional.of(exam));

        // 执行操作
        Optional<Exam> result = examService.getExamById(id);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getExamId());
    }

    @Test
    void getExamById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(examRepository.findById(id)).thenReturn(Optional.empty());

        // 执行操作
        Optional<Exam> result = examService.getExamById(id);

        // 验证结果
        assertFalse(result.isPresent());
    }

    @Test
    void getExamsByCourseId_Found() {
        // 准备数据
        Integer courseId = 1;
        List<Exam> exams = Arrays.asList(new Exam(), new Exam());
        when(examRepository.findByCourseId(courseId)).thenReturn(exams);

        // 执行操作
        List<Exam> result = examService.getExamsByCourseId(courseId);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getExamsByCourseId_NotFound() {
        // 准备数据
        Integer courseId = 1;
        when(examRepository.findByCourseId(courseId)).thenReturn(Collections.emptyList());

        // 执行操作
        List<Exam> result = examService.getExamsByCourseId(courseId);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void saveExam() {
        // 准备数据
        Exam exam = new Exam();
        exam.setExamId(1);
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        // 执行操作
        Exam savedExam = examService.saveExam(exam);

        // 验证结果
        assertNotNull(savedExam);
        assertEquals(1, savedExam.getExamId());
    }

    @Test
    void deleteExam() {
        // 准备数据
        Integer id = 1;
        doNothing().when(examRepository).deleteById(id);

        // 执行操作
        examService.deleteExam(id);

        // 验证删除操作
        verify(examRepository, times(1)).deleteById(id);
    }
}
