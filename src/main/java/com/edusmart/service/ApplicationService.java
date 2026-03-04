package com.edusmart.service;

import com.edusmart.model.Application;
import com.edusmart.model.User;
import com.edusmart.repository.ApplicationRepository;
import com.edusmart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public List<Application> getApplicationsByStatus(Application.Status status) {
        return applicationRepository.findByStatus(status);
    }

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

    public Application approveApplication(String id) {
        Optional<Application> optional = applicationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Application not found.");
        }
        Application application = optional.get();
        application.setStatus(Application.Status.APPROVED);
        applicationRepository.save(application);

        if (!userRepository.existsByEmail(application.getEmail())) {
            String rawPassword = generatePassword();

            User student = new User();
            student.setFirstName(application.getFirstNameFr());
            student.setLastName(application.getLastNameFr());
            student.setEmail(application.getEmail());
            student.setPassword(passwordEncoder.encode(rawPassword));
            student.setRole(User.Role.STUDENT);
            student.setDepartment(application.getDepartment());
            userRepository.save(student);

            String fullName = application.getFirstNameFr() + " " + application.getLastNameFr();
            emailService.sendWelcomeEmail(application.getEmail(), fullName, rawPassword, "Student");

            System.out.println("Student account created and email sent to: " + application.getEmail());
        }

        return application;
    }

    public Application rejectApplication(String id) {
        Optional<Application> optional = applicationRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Application not found.");
        }
        Application application = optional.get();
        application.setStatus(Application.Status.REJECTED);
        return applicationRepository.save(application);
    }

    public Optional<Application> getApplicationById(String id) {
        return applicationRepository.findById(id);
    }

    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}