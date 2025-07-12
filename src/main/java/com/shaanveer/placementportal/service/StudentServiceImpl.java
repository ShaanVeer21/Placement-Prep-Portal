package com.shaanveer.placementportal.service;

import com.shaanveer.placementportal.model.Quiz;
import com.shaanveer.placementportal.model.Student;
import com.shaanveer.placementportal.model.Topic;
import com.shaanveer.placementportal.repository.QuizRepository;
import com.shaanveer.placementportal.repository.StudentRepository;
import com.shaanveer.placementportal.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TopicRepository topicRepo;

    @Autowired
    private QuizRepository quizRepo;

    @Override
    public void markTopicAsCompleted(Long studentId, Long topicId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        topic.setCompleted(true);
        topicRepo.save(topic);

        student.getCompletedTopics().add(topic);
        studentRepo.save(student);
    }

    @Override
    public void markQuizAsCompleted(Long studentId, Long quizId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Quiz quiz = quizRepo.findByIdAndStudent(quizId, student)
                .orElseThrow(() -> new RuntimeException("Quiz not found for this student"));

        if (!quiz.isAttempted()) {
            quiz.setAttempted(true);      // ✅ Mark it as attempted
            quizRepo.save(quiz);          // ✅ Save the updated quiz

            student.getCompletedQuizzes().add(quiz);
            student.setTotalQuizzesAttempted(student.getTotalQuizzesAttempted() + 1);
            studentRepo.save(student);
        }
    }

}
