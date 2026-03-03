package com.edusmart.controller;

import com.edusmart.model.User;
import com.edusmart.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<User>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PostMapping
    public ResponseEntity<?> addTeacher(@RequestBody User teacher) {
        try {
            User saved = teacherService.addTeacher(teacher);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable String id, @RequestBody User teacher) {
        try {
            User updated = teacherService.updateTeacher(id, teacher);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher deleted successfully.");
    }
}