package com.edusmart.repository;

import com.edusmart.model.Internship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends MongoRepository<Internship, String> {
    List<Internship> findByStudentId(String studentId);
    List<Internship> findByStatus(Internship.Status status);
}