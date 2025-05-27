package com.diatoz.assesment.controllers;

import com.diatoz.assesment.dto.ApiResponse;
import com.diatoz.assesment.dto.JwtResponse;
import com.diatoz.assesment.dto.LoginRequest;
import com.diatoz.assesment.dto.RegisterRequest;
import com.diatoz.assesment.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new student")
    public ResponseEntity<ApiResponse> registerStudent(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerStudent(registerRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Student registered successfully"));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user and get JWT token")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}