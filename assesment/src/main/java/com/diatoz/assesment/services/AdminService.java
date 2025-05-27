package com.diatoz.assesment.services;

import com.diatoz.assesment.models.Enrollment;
import com.diatoz.assesment.models.Student;
import com.diatoz.assesment.repositories.EnrollmentRepository;
import com.diatoz.assesment.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }
}