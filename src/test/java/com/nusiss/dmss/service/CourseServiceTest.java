package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.CourseRepository;
import com.nusiss.dmss.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses() {
        // 准备数据
        when(courseRepository.findAll()).thenReturn(Arrays.asList(new Course(), new Course()));

        // 执行操作
        List<Course> courses = courseService.getAllCourses();

        // 验证结果
        assertNotNull(courses);
        assertEquals(2, courses.size());
    }

    @Test
    void getCourseById() {
        // 准备数据
        Integer id = 1;
        Course course = new Course();
        course.setCourseId(id);  // 使用Lombok生成的setter方法
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        // 执行操作
        Optional<Course> result = courseService.getCourseById(id);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getCourseId());
    }

    @Test
    void saveCourse() {
        // 准备数据
        Course course = new Course();
        course.setCourseId(1);  // 使用Lombok生成的setter方法
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // 执行操作
        Course savedCourse = courseService.saveCourse(course);

        // 验证结果
        assertNotNull(savedCourse);
        assertEquals(1, savedCourse.getCourseId());
    }

    @Test
    void deleteCourse() {
        // 准备数据
        Integer id = 1;

        // 执行操作
        courseService.deleteCourse(id);

        // 验证操作
        verify(courseRepository, times(1)).deleteById(id);
    }

    @Test
    void getCoursesWithFilters() {
        // 准备数据
        Course course = new Course();
        course.setCourseId(1);  // 使用Lombok生成的setter方法
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> page = new PageImpl<>(Arrays.asList(course));
        when(courseRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

        // 执行操作
        Page<Course> result = courseService.getCoursesWithFilters(course, pageable);

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}