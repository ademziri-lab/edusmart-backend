package com.edusmart.controller;

import com.edusmart.model.Application;
import com.edusmart.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicationController {

    private final ApplicationService applicationService;

    // GET all applications
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    // GET applications by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getByStatus(@PathVariable String status) {
        Application.Status s = Application.Status.valueOf(status.toUpperCase());
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(s));
    }

    // POST submit new application
    @PostMapping
    public ResponseEntity<?> submitApplication(@RequestBody Application application) {
        try {
            Application saved = applicationService.submitApplication(application);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT approve application
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveApplication(@PathVariable String id) {
        try {
            Application updated = applicationService.approveApplication(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT reject application
    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectApplication(@PathVariable String id) {
        try {
            Application updated = applicationService.rejectApplication(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE application
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Application deleted successfully.");
    }
}
