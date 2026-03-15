package com.edusmart.service;

import com.edusmart.model.User;
import com.edusmart.repository.UserRepository;
import com.edusmart.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName(),
                user.getDepartment() != null ? user.getDepartment() : "",
                user.getSubjects() != null ? user.getSubjects() : "",
                user.getClassName() != null ? user.getClassName() : "",
                user.getAssignedClasses() != null ? user.getAssignedClasses() : new ArrayList<>()
        );
    }

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}