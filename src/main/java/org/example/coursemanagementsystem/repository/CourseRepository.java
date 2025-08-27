package org.example.coursemanagementsystem.repository;

import org.example.coursemanagementsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findBySemester(String semester);
    List<Course> findByTeacher_TeacherId(Long teacherId);
    List<Course> findByDepartmentAndSemester(String department, String semester);

    @Query("SELECT c FROM Course c WHERE c.enrollments.size < c.maxStudents AND c.status = 'OPEN'")
    List<Course> findAvailableCourses();
}
