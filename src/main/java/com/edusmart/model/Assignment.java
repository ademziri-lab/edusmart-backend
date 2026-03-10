package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "assignments")
public class Assignment {

    @Id
    private String id;

    private String teacherId;
    private String teacherName;
    private String className;
    private String subject;
    private String title;
    private String dueDate;
    private String description;

    // Teacher's attached file
    private String fileName;
    private String fileData;  // Base64
    private String fileType;  // pdf or docx

    private List<Submission> submissions = new ArrayList<>();

    @Data
    public static class Submission {
        private String studentId;
        private String studentName;
        private String submittedAt;
        private Double grade;
        private String status = "PENDING"; // PENDING, SUBMITTED, GRADED

        // Student's submitted file
        private String fileName;
        private String fileData;  // Base64
        private String fileType;  // pdf or docx
    }
}