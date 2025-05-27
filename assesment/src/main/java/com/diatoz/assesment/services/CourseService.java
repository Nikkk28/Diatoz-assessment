package com.diatoz.assesment.services;

import com.diatoz.assesment.exceptions.CourseFullException;
import com.diatoz.assesment.exceptions.CourseNotFoundException;
import com.diatoz.assesment.exceptions.DuplicateEnrollmentException;
import com.diatoz.assesment.exceptions.StudentNotFoundException;
import com.diatoz.assesment.models.Course;
import com.diatoz.assesment.models.Enrollment;
import com.diatoz.assesment.models.Student;
import com.diatoz.assesment.repositories.CourseRepository;
import com.diatoz.assesment.repositories.EnrollmentRepository;
import com.diatoz.assesment.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
    }

    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);

        course.setName(courseDetails.getName());
        course.setInstructor(courseDetails.getInstructor());
        course.setCapacity(courseDetails.getCapacity());

        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    @Transactional
    public Enrollment enrollStudentInCourse(String username, Long courseId) {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new StudentNotFoundException("Student not found: " + username));

        Course course = getCourseById(courseId);

        // Check if student is already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new DuplicateEnrollmentException("Student is already enrolled in this course");
        }

        // Check if course has available spots
        if (!course.hasAvailableSpots()) {
            throw new CourseFullException("Course is full. No available spots.");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return enrollmentRepository.save(enrollment);
    }
}