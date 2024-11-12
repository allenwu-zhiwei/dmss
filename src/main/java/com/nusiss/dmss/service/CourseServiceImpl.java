package com.nusiss.dmss.service;

import com.nusiss.dmss.dao.CourseRepository;
import com.nusiss.dmss.dto.CourseReportDTO;
import com.nusiss.dmss.dto.CourseReportDTO.StudentGradeDTO;
import com.nusiss.dmss.entity.Course;
import com.nusiss.dmss.entity.Enrollment;
import com.nusiss.dmss.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AttendanceOperation findAttendanceByStudentStrategy;

    @Autowired
    private GradeService gradeService;

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

    @Override
    public CourseReportDTO getCourseReport(Integer courseId) {
        // 获取所有选修此课程的学生的 Enrollment 列表
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);

        // 获取该课程所有成绩
        List<Grade> grades = gradeService.getGradeByCourseId(courseId);

        // 将成绩按学生ID分组
        Map<Integer, Grade> studentGradesMap = grades.stream()
                .collect(Collectors.toMap(Grade::getStudentId, grade -> grade));

        // 获取学生成绩和姓名列表，并加入出勤率
        List<StudentGradeDTO> studentGrades = enrollments.stream()
                .map(enrollment -> {
                    Integer studentId = enrollment.getStudentId();
                    String studentName = enrollment.getStudentName();
                    Grade grade = studentGradesMap.get(studentId);

                    // 查询学生的出勤率
                    Double attendanceRate = findAttendanceByStudentStrategy.getAttendanceRateByStudentIdAndCourseId(studentId, courseId);

                    // 创建新的 StudentGradeDTO，包含出勤率信息
                    return new StudentGradeDTO(studentId, studentName, grade != null ? grade.getGrade() : null, attendanceRate);
                })
                .collect(Collectors.toList());

        // 计算平均分
        BigDecimal averageGrade = studentGrades.stream()
                .map(StudentGradeDTO::getGrade)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(studentGrades.size()), BigDecimal.ROUND_HALF_UP);

        // 计算分数分布
        List<Integer> scoreDistribution = new ArrayList<>(Collections.nCopies(10, 0));
        for (StudentGradeDTO studentGrade : studentGrades) {
            BigDecimal grade = studentGrade.getGrade();
            if (grade != null) {
                int index = grade.divide(new BigDecimal(10), BigDecimal.ROUND_DOWN).intValue();
                scoreDistribution.set(index, scoreDistribution.get(index) + 1);
            }
        }

        // 设置返回的 DTO
        CourseReportDTO courseReportDTO = new CourseReportDTO();
        courseReportDTO.setStudentGrades(studentGrades);
        courseReportDTO.setAverageGrade(averageGrade);
        courseReportDTO.setScoreDistribution(scoreDistribution);
        courseReportDTO.setTotalEnrolledStudents(enrollments.size());

        return courseReportDTO;
    }
}
