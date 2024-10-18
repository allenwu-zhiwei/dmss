package com.nusiss.dmss.service;


import com.nusiss.dmss.dao.GradeRepository;
import com.nusiss.dmss.entity.Grade;
import com.nusiss.dmss.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService{


    @Autowired
    private GradeRepository gradeRepository;


    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Optional<Grade> getGradeById(Integer id) {
        return gradeRepository.findById(id);
    }

    @Override
    public Optional<Grade> getGradeByStudentId(Integer studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public Optional<Grade> getGradeByCourseId(Integer courseId) {
        return gradeRepository.findByCourseId(courseId);
    }

    @Override
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public void deleteGrade(Integer id) {
        gradeRepository.deleteById(id);
    }


}
