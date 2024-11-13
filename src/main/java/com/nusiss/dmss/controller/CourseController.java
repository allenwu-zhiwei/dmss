package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.dto.CourseReportDTO;
import com.nusiss.dmss.entity.Course;
import com.nusiss.dmss.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 课程管理
 */
@RestController
@RequestMapping("/api/course")
@Slf4j
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 查询所有课程
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(new ApiResponse<>(true, "Courses retrieved successfully", courses));
    }

    /**
     * 通过id查询课程
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Integer id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Course retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Course not found", null)));
    }

    /**
     * 创建课程
     * @param course
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Course created successfully", savedCourse));
    }

    /**
     * 更新课程信息
     * @param id
     * @param updatedCourse
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Integer id, @RequestBody Course updatedCourse) {
        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isPresent()) {
            updatedCourse.setCourseId(id);
            Course savedCourse = courseService.saveCourse(updatedCourse);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course updated successfully", savedCourse));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Course not found", null));
        }
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Integer id) {
        Optional<Course> existingCourse = courseService.getCourseById(id);
        if (existingCourse.isPresent()) {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Course deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Course not found", null));
        }
    }

    /**
     * 分页
     * @param course
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<Course>>> getCoursesWithFilters(
            Course course,  // 使用Course实体来接收前端传入的查询条件
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (course == null) {
            course = new Course();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courses = courseService.getCoursesWithFilters(course, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Courses retrieved successfully", courses));
    }

    /**
     * 生成课程报告
     * @param courseId
     * @return
     */
    @GetMapping("/report/{courseId}")
    public CourseReportDTO getCourseReport(@PathVariable Integer courseId) {
        return courseService.getCourseReport(courseId);
    }
}