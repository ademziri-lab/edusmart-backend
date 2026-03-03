package com.edusmart.repository;

import com.edusmart.model.CollegeClass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeClassRepository extends MongoRepository<CollegeClass, String> {
    List<CollegeClass> findByDepartment(String department);
}