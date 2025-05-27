package com.diatoz.assesment.services;

import com.diatoz.assesment.dto.JwtResponse;
import com.diatoz.assesment.dto.LoginRequest;
import com.diatoz.assesment.dto.RegisterRequest;
import com.diatoz.assesment.exceptions.UserAlreadyExistsException;
import com.diatoz.assesment.models.Student;
import com.diatoz.assesment.models.User;
import com.diatoz.assesment.repositories.StudentRepository;
import com.diatoz.assesment.repositories.UserRepository;
import com.diatoz.assesment.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional
    public void registerStudent(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken!");
        }

        String password = registerRequest.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain at least one lowercase, one uppercase, one digit, and one special character.");
        }

        // Create user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(registerRequest.getRole());

        User savedUser = userRepository.save(user);

        if (savedUser.getRole().name().equals("STUDENT")) {
            Student student = new Student(savedUser);
            studentRepository.save(student);
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }


    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new JwtResponse(jwt, user.getUsername(), user.getRole().name());
    }
}