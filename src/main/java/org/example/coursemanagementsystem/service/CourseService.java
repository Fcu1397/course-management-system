package org.example.coursemanagementsystem.service;

import org.example.coursemanagementsystem.entity.Course;
import org.example.coursemanagementsystem.entity.Teacher;
import org.example.coursemanagementsystem.repository.CourseRepository;
import org.example.coursemanagementsystem.repository.TeacherRepository;
import org.example.coursemanagementsystem.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesBySemester(String semester) {
        return courseRepository.findBySemester(semester);
    }

    public List<Course> getAvailableCourses() {
        return courseRepository.findAvailableCourses();
    }

    public Course createCourse(CourseDTO dto) {
        // 檢查課程代碼是否已存在
        if (courseRepository.findByCourseCode(dto.getCourseCode()).isPresent()) {
            throw new RuntimeException("Course code already exists");
        }

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();
        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getCourseName());
        course.setCourseDescription(dto.getCourseDescription());
        course.setCredits(dto.getCredits());
        course.setDepartment(dto.getDepartment());
        course.setSemester(dto.getSemester());
        course.setMaxStudents(dto.getMaxStudents());
        course.setCourseType(dto.getCourseType());
        course.setTeacher(teacher);

        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            course.setTeacher(teacher);
        }

        course.setCourseName(dto.getCourseName());
        course.setCourseDescription(dto.getCourseDescription());
        course.setCredits(dto.getCredits());
        course.setMaxStudents(dto.getMaxStudents());

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
