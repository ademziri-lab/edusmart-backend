package com.edusmart.controller;

import com.edusmart.model.Assignment;
import com.edusmart.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    // Teacher posts a new assignment
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        return ResponseEntity.ok(assignmentService.createAssignment(assignment));
    }

    // Get assignments by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Assignment>> getByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByTeacher(teacherId));
    }

    // Get assignments by class (for students)
    @GetMapping("/class/{className}")
    public ResponseEntity<List<Assignment>> getByClass(@PathVariable String className) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByClass(className));
    }

    // Student submits their work
    @PostMapping("/{assignmentId}/submit")
    public ResponseEntity<Assignment> submitAssignment(
            @PathVariable String assignmentId,
            @RequestBody Assignment.Submission submission) {
        return ResponseEntity.ok(assignmentService.submitAssignment(assignmentId, submission));
    }

    // Teacher grades a submission
    @PutMapping("/{assignmentId}/grade/{studentId}")
    public ResponseEntity<Assignment> gradeSubmission(
            @PathVariable String assignmentId,
            @PathVariable String studentId,
            @RequestBody Map<String, Double> body) {
        Double grade = body.get("grade");
        return ResponseEntity.ok(assignmentService.gradeSubmission(assignmentId, studentId, grade));
    }

    // Delete assignment
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable String assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok("Assignment deleted successfully");
    }
}