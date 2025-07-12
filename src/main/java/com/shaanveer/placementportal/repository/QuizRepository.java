package com.shaanveer.placementportal.repository;

import com.shaanveer.placementportal.model.Quiz;
import com.shaanveer.placementportal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByStudent(Student student);
    Optional<Quiz> findByIdAndStudent(Long id, Student student);
}
