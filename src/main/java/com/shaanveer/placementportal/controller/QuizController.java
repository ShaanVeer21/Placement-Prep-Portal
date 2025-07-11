package com.shaanveer.placementportal.controller;

import com.shaanveer.placementportal.model.Quiz;
import com.shaanveer.placementportal.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepo;

    // ✅ Create a single quiz
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepo.save(quiz);
    }

    // ✅ Bulk create quizzes
    @PostMapping("/bulk")
    public List<Quiz> createQuizzes(@RequestBody List<Quiz> quizzes) {
        return quizRepo.saveAll(quizzes);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable Long id, @RequestBody Quiz updatedQuiz) {
        return quizRepo.findById(id).map(quiz -> {
            quiz.setTitle(updatedQuiz.getTitle());
            quiz.setTopic(updatedQuiz.getTopic());
            quiz.setTotalQuestions(updatedQuiz.getTotalQuestions());
            return quizRepo.save(quiz);
        }).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizRepo.deleteById(id);
    }
}
