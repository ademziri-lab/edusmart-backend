package com.edusmart;

import com.edusmart.model.CollegeClass;
import com.edusmart.model.User;
import com.edusmart.repository.CollegeClassRepository;
import com.edusmart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CollegeClassRepository collegeClassRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@edusmart.com")) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("EduSmart");
            admin.setEmail("admin@edusmart.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        } else {
            System.out.println("Admin user already exists.");
        }

        if (collegeClassRepository.count() == 0) {
            collegeClassRepository.saveAll(List.of(
                    createClass("DSI1", "Computer Science", "1st Year", 30, List.of("Algorithms", "Mathematics", "English")),
                    createClass("DSI2", "Computer Science", "2nd Year", 28, List.of("Data Structures", "Databases", "Web Development")),
                    createClass("DSI3", "Computer Science", "3rd Year", 25, List.of("Software Engineering", "Networks", "AI")),
                    createClass("GL1", "Civil Engineering", "1st Year", 22, List.of("Mechanics", "Mathematics", "Physics"))
            ));
            System.out.println("Default classes created successfully!");
        } else {
            System.out.println("✅ Classes already exist.");
        }
    }

    private CollegeClass createClass(String name, String department, String level, int studentCount, List<String> subjects) {
        CollegeClass c = new CollegeClass();
        c.setName(name);
        c.setDepartment(department);
        c.setLevel(level);
        c.setStudentCount(studentCount);
        c.setSubjects(subjects);
        return c;
    }
}
