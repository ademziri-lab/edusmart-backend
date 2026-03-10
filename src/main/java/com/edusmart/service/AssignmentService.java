package com.edusmart.service;

import com.edusmart.model.Assignment;
import com.edusmart.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // Teacher posts a new assignment
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    // Get all assignments by teacher
    public List<Assignment> getAssignmentsByTeacher(String teacherId) {
        return assignmentRepository.findByTeacherId(teacherId);
    }

    // Get all assignments by class (for students)
    public List<Assignment> getAssignmentsByClass(String className) {
        return assignmentRepository.findByClassName(className);
    }

    // Student submits their work
    public Assignment submitAssignment(String assignmentId, Assignment.Submission submission) {
        Optional<Assignment> optional = assignmentRepository.findById(assignmentId);
        if (optional.isEmpty()) throw new RuntimeException("Assignment not found");

        Assignment assignment = optional.get();

        // Set submission timestamp and status
        submission.setSubmittedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        submission.setStatus("SUBMITTED");

        // Remove old submission from same student if exists
        assignment.getSubmissions().removeIf(s -> s.getStudentId().equals(submission.getStudentId()));

        assignment.getSubmissions().add(submission);
        return assignmentRepository.save(assignment);
    }

    // Teacher grades a submission
    public Assignment gradeSubmission(String assignmentId, String studentId, Double grade) {
        Optional<Assignment> optional = assignmentRepository.findById(assignmentId);
        if (optional.isEmpty()) throw new RuntimeException("Assignment not found");

        Assignment assignment = optional.get();
        assignment.getSubmissions().forEach(s -> {
            if (s.getStudentId().equals(studentId)) {
                s.setGrade(grade);
                s.setStatus("GRADED");
            }
        });

        return assignmentRepository.save(assignment);
    }

    // Delete assignment
    public void deleteAssignment(String assignmentId) {
        assignmentRepository.deleteById(assignmentId);
    }
}