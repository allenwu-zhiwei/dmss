package com.nusiss.dmss.service;

import com.nusiss.dmss.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Integer id);
    Course saveCourse(Course course);
    void deleteCourse(Integer id);
}
