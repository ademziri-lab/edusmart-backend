package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "applications")
public class Application {

    @Id
    private String id;

    private String firstNameFr;
    private String lastNameFr;
    private String dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String nationality;
    private String cin;
    private String cinIssueDate;
    private String email;
    private String phone;
    private String emergencyContact;
    private String address;
    private String governorate;
    private String postalCode;
    private String bacYear;
    private String bacSession;
    private String bacSection;
    private String bacGrade;
    private String highSchool;
    private String department;
    private String notes;

    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}