package com.edusmart.service;

import com.edusmart.model.Internship;
import com.edusmart.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternshipService {

    private final InternshipRepository internshipRepository;

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public List<Internship> getInternshipsByStudentId(String studentId) {
        return internshipRepository.findByStudentId(studentId);
    }

    public Internship submitInternship(Internship internship) {
        internship.setStatus(Internship.Status.PENDING);
        return internshipRepository.save(internship);
    }

    public Internship approveInternship(String id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found."));
        internship.setStatus(Internship.Status.APPROVED);
        return internshipRepository.save(internship);
    }

    public Internship rejectInternship(String id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found."));
        internship.setStatus(Internship.Status.REJECTED);
        return internshipRepository.save(internship);
    }

    public void deleteInternship(String id) {
        internshipRepository.deleteById(id);
    }
}