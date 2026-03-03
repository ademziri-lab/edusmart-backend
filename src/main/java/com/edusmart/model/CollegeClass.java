package com.edusmart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "classes")
public class CollegeClass {

    @Id
    private String id;

    private String name;
    private String department;
    private String level;
    private int studentCount;
    private List<String> subjects;
}