package org.example.coursemanagementsystem.repository;

import org.example.coursemanagementsystem.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_StudentId(Long studentId);
    List<Enrollment> findByCourse_CourseId(Long courseId);
    List<Enrollment> findByStudent_StudentIdAndSemester(Long studentId, String semester);

    Optional<Enrollment> findByStudent_StudentIdAndCourse_CourseId(Long studentId, Long courseId);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.course.courseId = ?1 AND e.status = 'ENROLLED'")
    Integer countEnrolledStudents(Long courseId);

    @Query("SELECT AVG(e.score) FROM Enrollment e WHERE e.course.courseId = ?1 AND e.score IS NOT NULL")
    Double getAverageScore(Long courseId);
}
