package com.edusmart.repository;

import com.edusmart.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByStatus(Application.Status status);
    boolean existsByCin(String cin);
    boolean existsByEmail(String email);
}