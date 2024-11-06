package com.nusiss.dmss.dao;

import com.nusiss.dmss.entity.Course;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseName(String courseName);
   // Page<Course> findAll(Example<Course> example, Pageable pageable);
}
