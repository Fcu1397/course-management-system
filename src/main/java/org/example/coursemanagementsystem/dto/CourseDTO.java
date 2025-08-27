package org.example.coursemanagementsystem.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CourseDTO {
    private Long courseId;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotBlank(message = "Course name is required")
    private String courseName;

    private String courseDescription;

    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 10, message = "Credits cannot exceed 10")
    private Integer credits;

    private String department;
    private String semester;

    @Min(value = 1, message = "Max students must be at least 1")
    private Integer maxStudents = 50;

    private String courseType;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    private String teacherName;
    private Integer currentEnrollment;
}

