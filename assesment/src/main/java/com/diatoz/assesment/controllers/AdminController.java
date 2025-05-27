package com.diatoz.assesment.controllers;

import com.diatoz.assesment.models.Enrollment;
import com.diatoz.assesment.models.Student;
import com.diatoz.assesment.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin", description = "Admin management APIs")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/students")
    @Operation(summary = "Get all students (Admin only)")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = adminService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/enrollments")
    @Operation(summary = "Get all enrollments (Admin only)")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = adminService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }
}