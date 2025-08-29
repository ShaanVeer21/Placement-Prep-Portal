package com.shaanveer.placementportal.repository;

import com.shaanveer.placementportal.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByEmail(String email);

    
    @EntityGraph(attributePaths = {"completedTopics", "completedQuizzes"})
    Optional<Student> findWithDetailsByEmail(String email);
}
