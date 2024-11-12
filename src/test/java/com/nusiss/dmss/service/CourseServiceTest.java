package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.CourseRepository;
import com.nusiss.dmss.dto.CourseReportDTO;
import com.nusiss.dmss.dto.CourseReportDTO.StudentGradeDTO;
import com.nusiss.dmss.entity.Course;
import com.nusiss.dmss.entity.Enrollment;
import com.nusiss.dmss.entity.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private GradeService gradeService;

    @Mock
    private AttendanceOperation findAttendanceByStudentStrategy;

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
        course.setCourseId(id);
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
        course.setCourseId(1);
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
        course.setCourseId(1);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> page = new PageImpl<>(Arrays.asList(course));
        when(courseRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

        // 执行操作
        Page<Course> result = courseService.getCoursesWithFilters(course, pageable);

        // 验证结果
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getCourseReport() {
        // 准备数据
        Integer courseId = 1;

        // 模拟 Enrollments
        List<Enrollment> enrollments = Arrays.asList(
                Enrollment.builder()
                        .studentId(1)
                        .studentName("Alice")
                        .courseId(courseId)
                        .enrollmentDate(LocalDateTime.now())
                        .status(Enrollment.Status.ENROLLED)
                        .createDatetime(LocalDateTime.now())
                        .build(),
                Enrollment.builder()
                        .studentId(2)
                        .studentName("Bob")
                        .courseId(courseId)
                        .enrollmentDate(LocalDateTime.now())
                        .status(Enrollment.Status.ENROLLED)
                        .createDatetime(LocalDateTime.now())
                        .build()
        );
        when(enrollmentService.getEnrollmentsByCourseId(courseId)).thenReturn(enrollments);

        // 模拟 Grades
        List<Grade> grades = Arrays.asList(
                Grade.builder()
                        .studentId(1)
                        .courseId(courseId)
                        .grade(new BigDecimal("85.0"))
                        .createDatetime(LocalDateTime.now())
                        .build(),
                Grade.builder()
                        .studentId(2)
                        .courseId(courseId)
                        .grade(new BigDecimal("90.0"))
                        .createDatetime(LocalDateTime.now())
                        .build()
        );
        when(gradeService.getGradeByCourseId(courseId)).thenReturn(grades);

        // 模拟 Attendance Rates
        when(findAttendanceByStudentStrategy.getAttendanceRateByStudentIdAndCourseId(1, courseId)).thenReturn(0.9);
        when(findAttendanceByStudentStrategy.getAttendanceRateByStudentIdAndCourseId(2, courseId)).thenReturn(0.85);

        // 执行操作
        CourseReportDTO report = courseService.getCourseReport(courseId);

        // 验证结果
        assertNotNull(report);
        assertEquals(2, report.getTotalEnrolledStudents());

        List<StudentGradeDTO> studentGrades = report.getStudentGrades();
        assertNotNull(studentGrades);
        assertEquals(2, studentGrades.size());

        StudentGradeDTO aliceGrade = studentGrades.get(0);
        assertEquals(1, aliceGrade.getStudentId());
        assertEquals("Alice", aliceGrade.getStudentName());
        assertEquals(new BigDecimal("85.0"), aliceGrade.getGrade());
        assertEquals(0.9, aliceGrade.getAttendanceRate(), 0.01);

        StudentGradeDTO bobGrade = studentGrades.get(1);
        assertEquals(2, bobGrade.getStudentId());
        assertEquals("Bob", bobGrade.getStudentName());
        assertEquals(new BigDecimal("90.0"), bobGrade.getGrade());
        assertEquals(0.85, bobGrade.getAttendanceRate(), 0.01);

        assertEquals(new BigDecimal("87.5"), report.getAverageGrade());
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1, 1), report.getScoreDistribution());
    }
}