package com.nusiss.dmss.service;

import com.nusiss.dmss.entity.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();
    Optional<Enrollment> getEnrollmentById(Integer id);
    List<Enrollment> getEnrollmentsByStudentId(Integer studentId);
    List<Enrollment> getEnrollmentsByCourseId(Integer courseId);
    Enrollment saveEnrollment(Enrollment enrollment);
    void deleteEnrollment(Integer id);
}
