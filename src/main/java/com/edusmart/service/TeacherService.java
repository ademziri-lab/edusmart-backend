package com.edusmart.service;

import com.edusmart.model.User;
import com.edusmart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllTeachers() {
        return userRepository.findByRole(User.Role.TEACHER);
    }

    public User addTeacher(User teacher) {
        if (userRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        teacher.setRole(User.Role.TEACHER);
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        return userRepository.save(teacher);
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
}