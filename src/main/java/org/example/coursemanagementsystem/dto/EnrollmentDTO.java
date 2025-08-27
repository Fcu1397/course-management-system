package org.example.coursemanagementsystem.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class EnrollmentDTO {
    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private String semester;
    private LocalDate enrollmentDate;

    @Min(value = 0, message = "Score cannot be negative")
    @Max(value = 100, message = "Score cannot exceed 100")
    private Double score;

    private String gradeLetter;
    private Double attendanceRate;
    private String status;

    // 用於顯示的額外資訊
    private String studentName;
    private String courseName;
}
