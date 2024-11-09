package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Course;
import com.nusiss.dmss.service.CourseService;
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
        Course course = new Course();
        course.setCourseId(1);
        when(courseService.getCoursesWithFilters(eq(course), any())).thenReturn(new org.springframework.data.domain.PageImpl<>(Arrays.asList(course)));

        // 执行操作
        ResponseEntity<ApiResponse<org.springframework.data.domain.Page<Course>>> response = courseController.getCoursesWithFilters(course, 0, 10);

        // 验证结果
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Courses retrieved successfully", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertFalse(response.getBody().getData().isEmpty());
    }
}