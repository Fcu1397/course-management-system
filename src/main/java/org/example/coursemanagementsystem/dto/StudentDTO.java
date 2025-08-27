package org.example.coursemanagementsystem.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
public class StudentDTO {
    private Long studentId;

    @NotBlank(message = "Student code is required")
    private String studentCode;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private Integer enrollmentYear;
    private String status;
}
