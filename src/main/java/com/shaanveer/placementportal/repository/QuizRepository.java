package com.shaanveer.placementportal.repository;

import com.shaanveer.placementportal.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
