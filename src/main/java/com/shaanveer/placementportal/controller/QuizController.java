package com.shaanveer.placementportal.controller;

import com.shaanveer.placementportal.model.Quiz;
import com.shaanveer.placementportal.model.Student;
import com.shaanveer.placementportal.repository.QuizRepository;
import com.shaanveer.placementportal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student getCurrentStudent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Student student) {
            return student;
        }

        throw new RuntimeException("Unauthenticated access");
    }


    @GetMapping
    public List<Quiz> getAllQuizzes() {
        Student student = getCurrentStudent();
        return quizRepository.findByStudent(student);
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        Student student = getCurrentStudent();
        quiz.setStudent(student);
        return quizRepository.save(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quizDetails) {
        Student student = getCurrentStudent();
        Quiz quiz = quizRepository.findByIdAndStudent(id, student).orElseThrow();

        quiz.setTitle(quizDetails.getTitle());
        quiz.setDescription(quizDetails.getDescription());
        return ResponseEntity.ok(quizRepository.save(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        Student student = getCurrentStudent();
        Quiz quiz = quizRepository.findByIdAndStudent(id, student).orElseThrow();

        quizRepository.delete(quiz);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/attempt")
    public ResponseEntity<Quiz> attemptQuiz(@PathVariable Long id) {
        Student student = getCurrentStudent();
        Quiz quiz = quizRepository.findByIdAndStudent(id, student).orElseThrow();

        quiz.setAttempted(true);
        return ResponseEntity.ok(quizRepository.save(quiz));
    }
}
