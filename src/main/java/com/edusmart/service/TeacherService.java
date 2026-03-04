package com.edusmart.service;

import com.edusmart.model.User;
import com.edusmart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public List<User> getAllTeachers() {
        return userRepository.findByRole(User.Role.TEACHER);
    }

    public User addTeacher(User teacher) {
        if (userRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        String rawPassword = generatePassword();

        teacher.setRole(User.Role.TEACHER);
        teacher.setPassword(passwordEncoder.encode(rawPassword));
        User saved = userRepository.save(teacher);

        String fullName = teacher.getFirstName() + " " + teacher.getLastName();
        emailService.sendWelcomeEmail(teacher.getEmail(), fullName, rawPassword, "Teacher");

        System.out.println("✅ Teacher account created and email sent to: " + teacher.getEmail());

        return saved;
    }

    public User updateTeacher(String id, User updated) {
        User teacher = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found."));
        teacher.setFirstName(updated.getFirstName());
        teacher.setLastName(updated.getLastName());
        teacher.setEmail(updated.getEmail());
        return userRepository.save(teacher);
    }

    public void deleteTeacher(String id) {
        userRepository.deleteById(id);
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}