package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.CourseRepository;
import com.nusiss.dmss.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<Course> getCoursesWithFilters(Course course, Pageable pageable) {
        // 创建一个Example对象，用来执行动态查询
        Example<Course> example = Example.of(course);
        return courseRepository.findAll(example, pageable);
    }
}
