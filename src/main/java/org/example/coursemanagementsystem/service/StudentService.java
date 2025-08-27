package org.example.coursemanagementsystem.service;

import org.example.coursemanagementsystem.entity.Student;
import org.example.coursemanagementsystem.repository.StudentRepository;
import org.example.coursemanagementsystem.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(StudentDTO dto) {
        // 檢查email是否已存在
        if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Student student = new Student();
        student.setStudentCode(dto.getStudentCode());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setEnrollmentYear(dto.getEnrollmentYear());

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchByName(keyword);
    }
}

