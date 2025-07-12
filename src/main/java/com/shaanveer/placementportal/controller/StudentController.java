package com.shaanveer.placementportal.controller;

import com.shaanveer.placementportal.model.Quiz;
import com.shaanveer.placementportal.model.Student;
import com.shaanveer.placementportal.model.Topic;
import com.shaanveer.placementportal.repository.StudentRepository;
import com.shaanveer.placementportal.service.JwtService;
import com.shaanveer.placementportal.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtService jwtService;

    // ✅ Get current student
    @GetMapping("/me")
    public ResponseEntity<Student> getLoggedInStudent(HttpServletRequest request) {
        String token = extractToken(request);
        if (token != null) {
            String email = jwtService.extractUsername(token);
            Student student = studentRepo.findWithDetailsByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.badRequest().build();
    }

    // ✅ Mark topic as completed (uses token)
    @PutMapping("/complete-topic/{topicId}")
    public ResponseEntity<String> markTopicCompleted(HttpServletRequest request,
                                                     @PathVariable Long topicId) {
        Student student = getStudentFromToken(request);
        studentService.markTopicAsCompleted(student.getId(), topicId);
        return ResponseEntity.ok("Topic marked as completed.");
    }

    // ✅ Mark quiz as completed (uses token)
    @PutMapping("/complete-quiz/{quizId}")
    public ResponseEntity<String> markQuizCompleted(HttpServletRequest request,
                                                    @PathVariable Long quizId) {
        Student student = getStudentFromToken(request);
        studentService.markQuizAsCompleted(student.getId(), quizId);
        return ResponseEntity.ok("Quiz marked as completed.");
    }

    // 🔧 Internal / Admin only
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepo.findById(id).map(student -> {
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            student.setCurrentTopic(updatedStudent.getCurrentTopic());
            student.setTotalQuizzesAttempted(updatedStudent.getTotalQuizzesAttempted());
            student.setPersonalTopics(updatedStudent.getPersonalTopics());
            student.setCompletedTopics(updatedStudent.getCompletedTopics());
            student.setCompletedQuizzes(updatedStudent.getCompletedQuizzes());
            return studentRepo.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
    }

    @PostMapping("/{id}/topics")
    public Student addTopics(@PathVariable Long id, @RequestBody Set<Topic> topics) {
        return studentRepo.findById(id).map(student -> {
            student.getPersonalTopics().addAll(topics);
            return studentRepo.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PostMapping("/{id}/quizzes")
    public Student addQuizzes(@PathVariable Long id, @RequestBody List<Quiz> quizzes) {
        return studentRepo.findById(id).map(student -> {
            student.getCompletedQuizzes().addAll(quizzes);
            student.setTotalQuizzesAttempted(student.getTotalQuizzesAttempted() + quizzes.size());
            return studentRepo.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // ⛏️ Helper Methods
    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (token != null && token.startsWith("Bearer ")) ? token.substring(7) : null;
    }

    private Student getStudentFromToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) throw new RuntimeException("Missing token");
        String email = jwtService.extractUsername(token);
        return studentRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
