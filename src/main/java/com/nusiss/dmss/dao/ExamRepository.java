package com.nusiss.dmss.dao;


import com.nusiss.dmss.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Optional<Exam> findByCourseId(Integer courseId);
}