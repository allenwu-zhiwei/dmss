package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    //List<Enrollment> findByStudent_StudentId(Integer studentId);
    List<Enrollment> findByStudentId(Integer studentId);
    List<Enrollment> findByCourseId(Integer courseId);
}
