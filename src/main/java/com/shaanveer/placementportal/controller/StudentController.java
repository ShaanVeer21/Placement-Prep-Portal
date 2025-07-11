package com.shaanveer.placementportal.controller;

import com.shaanveer.placementportal.model.Quiz;
import com.shaanveer.placementportal.model.Student;
import com.shaanveer.placementportal.model.Topic;
import com.shaanveer.placementportal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

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
        return studentRepo.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setCurrentTopic(updatedStudent.getCurrentTopic());
                    student.setTotalQuizzesAttempted(updatedStudent.getTotalQuizzesAttempted());
                    student.setTopics(updatedStudent.getTopics());
                    student.setCompletedQuizzes(updatedStudent.getCompletedQuizzes());
                    return studentRepo.save(student);
                }).orElseThrow(() -> new RuntimeException("Student not found"));
    }


    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
    }

    // ✅ Add topics to a student
    @PostMapping("/{id}/topics")
    public Student addTopics(@PathVariable Long id, @RequestBody List<Topic> topics) {
        return studentRepo.findById(id)
                .map(student -> {
                    student.getTopics().addAll(topics);
                    return studentRepo.save(student);
                }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // ✅ Add quizzes to a student
    @PostMapping("/{id}/quizzes")
    public Student addQuizzes(@PathVariable Long id, @RequestBody List<Quiz> quizzes) {
        return studentRepo.findById(id)
                .map(student -> {
                    student.getCompletedQuizzes().addAll(quizzes);
                    student.setTotalQuizzesAttempted(student.getTotalQuizzesAttempted() + quizzes.size());
                    return studentRepo.save(student);
                }).orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
