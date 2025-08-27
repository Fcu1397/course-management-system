package org.example.coursemanagementsystem.service;

import org.example.coursemanagementsystem.entity.*;
import org.example.coursemanagementsystem.repository.*;
import org.example.coursemanagementsystem.dto.EnrollmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Enrollment enrollStudent(EnrollmentDTO dto) {
        // 檢查學生是否存在
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 檢查課程是否存在
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 檢查是否已經選課
        if (enrollmentRepository.findByStudent_StudentIdAndCourse_CourseId(
                dto.getStudentId(), dto.getCourseId()).isPresent()) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        // 檢查課程人數是否已滿
        Integer enrolledCount = enrollmentRepository.countEnrolledStudents(dto.getCourseId());
        if (enrolledCount >= course.getMaxStudents()) {
            throw new RuntimeException("Course is full");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(dto.getSemester());
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateGrade(Long enrollmentId, Double score) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setScore(score);
        return enrollmentRepository.save(enrollment);
    }

    public void dropCourse(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudent_StudentId(studentId);
    }

    public List<Enrollment> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourse_CourseId(courseId);
    }
}
