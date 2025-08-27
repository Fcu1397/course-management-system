package org.example.coursemanagementsystem.repository;

import org.example.coursemanagementsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
    List<Teacher> findByDepartment(String department);
    List<Teacher> findByStatus(TeacherStatus status);
}
