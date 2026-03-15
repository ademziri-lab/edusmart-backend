package com.edusmart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String fullName, String password, String role) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to EduSmart — Your Account Details");
        message.setText(
                "Dear " + fullName + ",\n\n" +
                        "Your EduSmart account has been created successfully.\n\n" +
                        "Here are your login credentials:\n" +
                        "Email: " + toEmail + "\n" +
                        "Password: " + password + "\n" +
                        "Role: " + role + "\n\n" +

                        "Best regards,\n" +
                        "EduSmart Team"
        );
        mailSender.send(message);
        System.out.println("Welcome email sent to: " + toEmail);
    }
}