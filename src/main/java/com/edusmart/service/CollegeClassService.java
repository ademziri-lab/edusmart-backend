package com.edusmart.service;

import com.edusmart.model.CollegeClass;
import com.edusmart.repository.CollegeClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollegeClassService {

    private final CollegeClassRepository collegeClassRepository;

    public List<CollegeClass> getAllClasses() {
        return collegeClassRepository.findAll();
    }

    public CollegeClass addClass(CollegeClass collegeClass) {
        return collegeClassRepository.save(collegeClass);
    }

    public CollegeClass updateClass(String id, CollegeClass updated) {
        CollegeClass existing = collegeClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found."));
        existing.setName(updated.getName());
        existing.setDepartment(updated.getDepartment());
        existing.setLevel(updated.getLevel());
        existing.setStudentCount(updated.getStudentCount());
        existing.setSubjects(updated.getSubjects());
        return collegeClassRepository.save(existing);
    }

    public void deleteClass(String id) {
        collegeClassRepository.deleteById(id);
    }
}