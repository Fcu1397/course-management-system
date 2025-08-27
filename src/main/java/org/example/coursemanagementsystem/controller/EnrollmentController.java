package org.example.coursemanagementsystem.controller;

import org.example.coursemanagementsystem.entity.Enrollment;
import org.example.coursemanagementsystem.service.EnrollmentService;
import org.example.coursemanagementsystem.dto.EnrollmentDTO;
import org.example.coursemanagementsystem.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Enrollment>> enrollStudent(@Valid @RequestBody EnrollmentDTO dto) {
        try {
            Enrollment enrollment = enrollmentService.enrollStudent(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Enrollment successful", enrollment));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}/grade")
    public ResponseEntity<ApiResponse<Enrollment>> updateGrade(
            @PathVariable Long id,
            @RequestParam Double score) {
        try {
            Enrollment enrollment = enrollmentService.updateGrade(id, score);
            return ResponseEntity.ok(ApiResponse.success("Grade updated successfully", enrollment));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}/drop")
    public ResponseEntity<ApiResponse<Void>> dropCourse(@PathVariable Long id) {
        enrollmentService.dropCourse(id);
        return ResponseEntity.ok(ApiResponse.success("Course dropped successfully", null));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getStudentEnrollments(@PathVariable Long studentId) {
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<Enrollment>>> getCourseEnrollments(@PathVariable Long courseId) {
        List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
        return ResponseEntity.ok(ApiResponse.success(enrollments));
    }
}
