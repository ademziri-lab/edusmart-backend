package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "internships")
public class Internship {

    @Id
    private String id;

    private String studentId;
    private String studentName;
    private String company;
    private String duration;
    private String startDate;
    private String description;

    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}