package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Enrollment;
import com.nusiss.dmss.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 选课信息管理
 */
@RestController
@RequestMapping("/api/enrollment")
@Slf4j
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * 查询所有选课信息
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrollment>>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(new ApiResponse<>(true, "Enrollments retrieved successfully", enrollments));
    }

    /**
     * 根据选课id查询选课信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> getEnrollmentById(@PathVariable Integer id) {
        Optional<Enrollment> enrollment = enrollmentService.getEnrollmentById(id);
        return enrollment.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Enrollment retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Enrollment not found", null)));
    }

    /**
     * 根据学生id查询所有选课信息
     * @param studentId
     * @return
     */
    @GetMapping("/user/{studentId}")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getEnrollmentsByStudentrId(@PathVariable Integer studentId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Enrollments retrieved successfully", enrollments));
    }

    /**
     * 根据课程id查询所有选课信息
     * @param courseId
     * @return
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getEnrollmentsByCourseId(@PathVariable Integer courseId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Enrollments retrieved successfully", enrollments));
    }

    /**
     * 新建选课信息
     * @param enrollment
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Enrollment>> createEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment savedEnrollment = enrollmentService.saveEnrollment(enrollment);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Enrollment created successfully", savedEnrollment));
    }

    /**
     * 更新选课信息
     * @param id
     * @param updatedEnrollment
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> updateEnrollment(@PathVariable Integer id, @RequestBody Enrollment updatedEnrollment) {
        Optional<Enrollment> existingEnrollment = enrollmentService.getEnrollmentById(id);
        if (existingEnrollment.isPresent()) {
            updatedEnrollment.setEnrollmentId(id);
            Enrollment savedEnrollment = enrollmentService.saveEnrollment(updatedEnrollment);
            return ResponseEntity.ok(new ApiResponse<>(true, "Enrollment updated successfully", savedEnrollment));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Enrollment not found", null));
        }
    }

    /**
     * 删除选课信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEnrollment(@PathVariable Integer id) {
        Optional<Enrollment> existingEnrollment = enrollmentService.getEnrollmentById(id);
        if (existingEnrollment.isPresent()) {
            enrollmentService.deleteEnrollment(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Enrollment deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Enrollment not found", null));
        }
    }
}
