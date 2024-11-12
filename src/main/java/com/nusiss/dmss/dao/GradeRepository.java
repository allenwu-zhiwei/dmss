package com.nusiss.dmss.dao;


import com.nusiss.dmss.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudentId(Integer studentId);

    List<Grade> findByCourseId(Integer courseId);
}