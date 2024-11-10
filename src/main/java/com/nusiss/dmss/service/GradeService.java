package com.nusiss.dmss.service;


import com.nusiss.dmss.entity.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeService {

    public List<Grade> getAllGrades();

    public Optional<Grade> getGradeById(Integer id);

    public List<Grade> getGradeByStudentId(Integer studentId);

    public List<Grade> getGradeByCourseId(Integer courseId);

    public Grade saveGrade(Grade grade);

    public void deleteGrade(Integer id);

}
