package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    //List<Enrollment> findByStudent_StudentId(Integer studentId);
    List<Enrollment> findByStudentId(Integer studentId);
    List<Enrollment> findByCourseId(Integer courseId);

    //@Query("SELECT e.studentId FROM Enrollment e WHERE e.courseId = :courseId")
    //List<Integer> findStudentIdsByCourseId(Integer courseId);
}
