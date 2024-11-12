package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.dto.CourseReportDTO;
import com.nusiss.dmss.entity.Course;
import com.nusiss.dmss.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses() {
        // 准备数据
        when(courseService.getAllCourses()).thenReturn(Arrays.asList(
                new Course(),
                new Course()
        ));

        // 执行操作
        ResponseEntity<ApiResponse<List<Course>>> response = courseController.getAllCourses();

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Courses retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().size());
    }

    @Test
    void getCourseById() {
        // 准备数据
        Integer id = 1;
        Course course = new Course();
        course.setCourseId(id);
        when(courseService.getCourseById(id)).thenReturn(Optional.of(course));

        // 执行操作
        ResponseEntity<ApiResponse<Course>> response = courseController.getCourseById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Course retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getCourseId());
    }

    @Test
    void getCourseById_NotFound() {
        // 准备数据
        Integer id = 1;
        when(courseService.getCourseById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Course>> response = courseController.getCourseById(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Course not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void createCourse() {
        // 准备数据
        Course course = new Course();
        course.setCourseId(1);
        when(courseService.saveCourse(course)).thenReturn(course);

        // 执行操作
        ResponseEntity<ApiResponse<Course>> response = courseController.createCourse(course);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Course created successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getCourseId());
    }

    @Test
    void updateCourse() {
        // 准备数据
        Integer id = 1;
        Course updatedCourse = new Course();
        updatedCourse.setCourseId(id);
        when(courseService.getCourseById(id)).thenReturn(Optional.of(new Course()));
        when(courseService.saveCourse(updatedCourse)).thenReturn(updatedCourse);

        // 执行操作
        ResponseEntity<ApiResponse<Course>> response = courseController.updateCourse(id, updatedCourse);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Course updated successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(id, response.getBody().getData().getCourseId());
    }

    @Test
    void updateCourse_NotFound() {
        // 准备数据
        Integer id = 1;
        Course updatedCourse = new Course();
        updatedCourse.setCourseId(id);
        when(courseService.getCourseById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<Course>> response = courseController.updateCourse(id, updatedCourse);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Course not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteCourse() {
        // 准备数据
        Integer id = 1;
        when(courseService.getCourseById(id)).thenReturn(Optional.of(new Course()));

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = courseController.deleteCourse(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Course deleted successfully", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void deleteCourse_NotFound() {
        // 准备数据
        Integer id = 1;
        when(courseService.getCourseById(id)).thenReturn(Optional.empty());

        // 执行操作
        ResponseEntity<ApiResponse<String>> response = courseController.deleteCourse(id);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Course not found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    }

    @Test
    void getCoursesWithFilters() {
        // 准备数据
        int page = 0;
        int size = 10;
        Course course = new Course();
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courses = new PageImpl<>(Arrays.asList(
                new Course(),
                new Course()
        ));
        when(courseService.getCoursesWithFilters(eq(course), eq(pageable))).thenReturn(courses);

        // 执行操作
        ResponseEntity<ApiResponse<Page<Course>>> response = courseController.getCoursesWithFilters(course, page, size);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Courses retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(2, response.getBody().getData().getContent().size());
    }

    @Test
    void getCourseReport() {
        // 准备数据
        Integer courseId = 1;
        CourseReportDTO report = new CourseReportDTO();
        when(courseService.getCourseReport(courseId)).thenReturn(report);

        // 执行操作
        CourseReportDTO response = courseController.getCourseReport(courseId);

        // 验证结果
        assertNotNull(response);
        assertEquals(report, response);
    }
}