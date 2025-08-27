package org.example.coursemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "course_id", "semester"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate = LocalDate.now();

    private String semester;

    private Double score;

    @Column(name = "grade_letter")
    private String gradeLetter;

    @Column(name = "attendance_rate")
    private Double attendanceRate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        // 自動計算等第
        if (score != null) {
            calculateGradeLetter();
        }
    }

    private void calculateGradeLetter() {
        if (score >= 90) gradeLetter = "A+";
        else if (score >= 85) gradeLetter = "A";
        else if (score >= 80) gradeLetter = "B+";
        else if (score >= 75) gradeLetter = "B";
        else if (score >= 70) gradeLetter = "C+";
        else if (score >= 65) gradeLetter = "C";
        else if (score >= 60) gradeLetter = "D";
        else gradeLetter = "F";
    }
}

enum EnrollmentStatus {
    ENROLLED, DROPPED, COMPLETED, FAILED
}
