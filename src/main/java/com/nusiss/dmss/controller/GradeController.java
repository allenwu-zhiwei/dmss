package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Grade;
import com.nusiss.dmss.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Grade>>> getAllGrades() {
        List<Grade> grades = gradeService.getAllGrades();
        return ResponseEntity.ok(new ApiResponse<>(true, "Grades retrieved successfully", grades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Grade>> getGradeById(@PathVariable Integer id) {
        Optional<Grade> grade = gradeService.getGradeById(id);
        return grade.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Grade retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Grade not found", null)));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<ApiResponse<Grade>> getGradeByStudentId(@PathVariable Integer id) {
        Optional<Grade> grade = gradeService.getGradeByStudentId(id);
        return grade.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Grade retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Grade not found", null)));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<ApiResponse<Grade>> getGradeByCourseId(@PathVariable Integer id) {
        Optional<Grade> grade = gradeService.getGradeByCourseId(id);
        return grade.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Grade retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Grade not found", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Grade>> createGrade(@RequestBody Grade grade) {
        Grade savedGrade = gradeService.saveGrade(grade);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Grade created successfully", savedGrade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Grade>> updateGrade(@PathVariable Integer id, @RequestBody Grade updatedGrade) {
        Optional<Grade> existingGrade = gradeService.getGradeById(id);
        if (existingGrade.isPresent()) {
            updatedGrade.setGradeId(id);
            Grade savedGrade = gradeService.saveGrade(updatedGrade);
            return ResponseEntity.ok(new ApiResponse<>(true, "Grade updated successfully", savedGrade));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Grade not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteGrade(@PathVariable Integer id) {
        Optional<Grade> existingGrade = gradeService.getGradeById(id);
        if (existingGrade.isPresent()) {
            gradeService.deleteGrade(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Grade deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Grade not found", null));
        }
    }
}
