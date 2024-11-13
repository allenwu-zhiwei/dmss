package com.nusiss.dmss.controller;

import com.nusiss.dmss.config.ApiResponse;
import com.nusiss.dmss.entity.Exam;
import com.nusiss.dmss.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Exam>>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(new ApiResponse<>(true, "Exams retrieved successfully", exams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Exam>> getExamById(@PathVariable Integer id) {
        Optional<Exam> exam = examService.getExamById(id);
        return exam.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "Exam retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(404).body(new ApiResponse<>(false, "Exam not found", null)));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<ApiResponse<List<Exam>>> getExamsByCourseId(@PathVariable Integer id) {
        List<Exam> exams = examService.getExamsByCourseId(id);
        if (!exams.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Exams retrieved successfully", exams));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Exams not found", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Exam>> createExam(@RequestBody Exam exam) {
        Exam savedExam = examService.saveExam(exam);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Exam created successfully", savedExam));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Exam>> updateExam(@PathVariable Integer id, @RequestBody Exam updatedExam) {
        Optional<Exam> existingExam = examService.getExamById(id);
        if (existingExam.isPresent()) {
            updatedExam.setExamId(id);
            Exam savedExam = examService.saveExam(updatedExam);
            return ResponseEntity.ok(new ApiResponse<>(true, "Exam updated successfully", savedExam));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Exam not found", null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExam(@PathVariable Integer id) {
        Optional<Exam> existingExam = examService.getExamById(id);
        if (existingExam.isPresent()) {
            examService.deleteExam(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Exam deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Exam not found", null));
        }
    }
}
