package com.nusiss.dmss.dao;


import com.nusiss.dmss.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

    List<Exam> findByCourseId(Integer courseId);
}