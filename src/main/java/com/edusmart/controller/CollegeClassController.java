package com.edusmart.controller;

import com.edusmart.model.CollegeClass;
import com.edusmart.service.CollegeClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CollegeClassController {

    private final CollegeClassService collegeClassService;

    @GetMapping
    public ResponseEntity<List<CollegeClass>> getAllClasses() {
        return ResponseEntity.ok(collegeClassService.getAllClasses());
    }

    @PostMapping
    public ResponseEntity<?> addClass(@RequestBody CollegeClass collegeClass) {
        try {
            CollegeClass saved = collegeClassService.addClass(collegeClass);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass(@PathVariable String id, @RequestBody CollegeClass collegeClass) {
        try {
            CollegeClass updated = collegeClassService.updateClass(id, collegeClass);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable String id) {
        collegeClassService.deleteClass(id);
        return ResponseEntity.ok("Class deleted successfully.");
    }
}