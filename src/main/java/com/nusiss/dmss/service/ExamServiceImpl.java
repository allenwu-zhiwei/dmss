package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.ExamRepository;
import com.nusiss.dmss.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Optional<Exam> getExamById(Integer id) {
        return examRepository.findById(id);
    }

    @Override
    public List<Exam> getExamsByCourseId(Integer courseId) {
        return examRepository.findByCourseId(courseId);
    }

    @Override
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public void deleteExam(Integer id) {
        examRepository.deleteById(id);
    }
}
