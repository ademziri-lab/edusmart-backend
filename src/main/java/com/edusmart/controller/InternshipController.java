package com.edusmart.controller;

import com.edusmart.model.Internship;
import com.edusmart.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public ResponseEntity<List<Internship>> getAllInternships() {
        return ResponseEntity.ok(internshipService.getAllInternships());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Internship>> getByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(internshipService.getInternshipsByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<?> submitInternship(@RequestBody Internship internship) {
        try {
            Internship saved = internshipService.submitInternship(internship);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveInternship(@PathVariable String id) {
        try {
            Internship updated = internshipService.approveInternship(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectInternship(@PathVariable String id) {
        try {
            Internship updated = internshipService.rejectInternship(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInternship(@PathVariable String id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.ok("Internship deleted successfully.");
    }
}
