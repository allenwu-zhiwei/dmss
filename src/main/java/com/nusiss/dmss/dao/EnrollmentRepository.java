package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByUser_UserId(Integer userId);
    List<Enrollment> findByCourse_CourseId(Integer courseId);
}
