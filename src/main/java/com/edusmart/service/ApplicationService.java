package com.edusmart.service;

import com.edusmart.model.Application;
import com.edusmart.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get applications by status
    public List<Application> getApplicationsByStatus(Application.Status status) {
        return applicationRepository.findByStatus(status);
    }

    // Submit a new application
    public Application submitApplication(Application application) {
        if (applicationRepository.existsByCin(application.getCin())) {
            throw new RuntimeException("An application with this CIN already exists.");
        }
        if (applicationRepository.existsByEmail(application.getEmail())) {
            throw new RuntimeException("An application with this email already exists.");
        }
        application.setStatus(Application.Status.PENDING);
        return applicationRepository.save(application);
    }

    // Approve an application
    public Application approveApplication(String id) {
        Optional<Application> optional = applicationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Application not found.");
        }
        Application application = optional.get();
        application.setStatus(Application.Status.APPROVED);
        return applicationRepository.save(application);
    }

    // Reject an application
    public Application rejectApplication(String id) {
        Optional<Application> optional = applicationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Application not found.");
        }
        Application application = optional.get();
        application.setStatus(Application.Status.REJECTED);
        return applicationRepository.save(application);
    }

    // Get application by id
    public Optional<Application> getApplicationById(String id) {
        return applicationRepository.findById(id);
    }

    // Delete application
    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }
}