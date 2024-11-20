package com.nusiss.dmss.entity.factory;

import com.nusiss.dmss.entity.*;

import java.time.LocalDate;

public class ExamFactory {

    /**
     * Factory method to create Exam instances based on examType.
     *
     * @param examType        The type of exam (e.g., MIDTERM, FINAL, RETAKE, ONLINE).
     * @param courseId        The course ID associated with the exam.
     * @param examName        The name of the exam.
     * @param examDate        The scheduled date of the exam.
     * @param durationMinutes Duration of the exam in minutes.
     * @param location        The physical location for the exam (optional for online exams).
     * @param platformUrl     The platform URL for online exams (only required for ONLINE type).
     * @param accessCode      The access code for online exams (only required for ONLINE type).
     * @param retakeReason    The reason for a retake (only required for RETAKE type).
     * @param originalDate    The original exam date for retakes (only required for RETAKE type).
     * @return The appropriate Exam subclass instance.
     */
    public static Exam createExam(
            String examType,
            Integer courseId,
            String examName,
            LocalDate examDate,
            Integer durationMinutes,
            String location,
            String platformUrl,
            String accessCode,
            String retakeReason,
            LocalDate originalDate) {

        switch (examType.toUpperCase()) {

            case "FINAL":
                return new FinalExam(courseId, examName, examDate, durationMinutes, location);

            case "RETAKE":
                if (retakeReason == null || originalDate == null) {
                    throw new IllegalArgumentException("RetakeExam requires retakeReason and originalDate.");
                }
                return new RetakeExam(courseId, examName, examDate, location, retakeReason, originalDate);

            case "ONLINE":
                if (platformUrl == null || platformUrl.isEmpty()) {
                    throw new IllegalArgumentException("OnlineExam requires a platformUrl.");
                }
                return new OnlineExam(courseId, examName, examDate, durationMinutes, platformUrl, accessCode);

            default:
                throw new IllegalArgumentException("Invalid exam type: " + examType);
        }
    }
}
