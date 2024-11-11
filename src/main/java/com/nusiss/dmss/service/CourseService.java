package com.nusiss.dmss.service;

import com.nusiss.dmss.dto.CourseReportDTO;
import com.nusiss.dmss.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Integer id);
    Course saveCourse(Course course);
    void deleteCourse(Integer id);
    Page<Course> getCoursesWithFilters(Course course, Pageable pageable);

    CourseReportDTO getCourseReport(Integer courseId);
}
