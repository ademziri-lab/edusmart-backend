package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private String department;
    private String subjects;
    private String className;
    private List<String> assignedClasses = new ArrayList<>();

    public enum Role {
        ADMIN, TEACHER, STUDENT
    }
}