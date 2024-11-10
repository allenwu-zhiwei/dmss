package com.nusiss.dmss.service;

import com.nusiss.dmss.entity.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamService {

    List<Exam> getAllExams();

    Optional<Exam> getExamById(Integer id);

    List<Exam> getExamsByCourseId(Integer courseId);

    Exam saveExam(Exam exam);

    void deleteExam(Integer id);
}
