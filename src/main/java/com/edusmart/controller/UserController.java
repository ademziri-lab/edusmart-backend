package com.edusmart.controller;

import com.edusmart.model.User;
import com.edusmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all students
    @GetMapping("/students")
    public ResponseEntity<List<User>> getStudents() {
        return ResponseEntity.ok(userRepository.findByRole(User.Role.STUDENT));
    }

    // Get all teachers
    @GetMapping("/teachers")
    public ResponseEntity<List<User>> getTeachers() {
        return ResponseEntity.ok(userRepository.findByRole(User.Role.TEACHER));
    }

    // Assign a class to a student
    @PutMapping("/{id}/assign-class")
    public ResponseEntity<User> assignClass(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String className = body.get("className");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setClassName(className);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    // Assign multiple classes to a teacher
    @PutMapping("/{id}/assign-classes")
    public ResponseEntity<User> assignClasses(
            @PathVariable String id,
            @RequestBody Map<String, List<String>> body) {
        List<String> assignedClasses = body.get("assignedClasses");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAssignedClasses(assignedClasses);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}