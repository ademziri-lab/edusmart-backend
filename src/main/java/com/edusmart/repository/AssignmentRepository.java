package com.edusmart.repository;

import com.edusmart.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    List<Assignment> findByTeacherId(String teacherId);
    List<Assignment> findByClassName(String className);
}