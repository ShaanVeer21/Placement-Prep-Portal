package com.shaanveer.placementportal.service;

import com.shaanveer.placementportal.model.*;
import com.shaanveer.placementportal.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private TopicRepository topicRepo;

    @Mock
    private QuizRepository quizRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarkTopicAsCompleted() {
        Long studentId = 1L;
        Long topicId = 2L;

        Student student = new Student();
        Topic topic = new Topic();
        topic.setName("DBMS");

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(topicRepo.findById(topicId)).thenReturn(Optional.of(topic));

        studentService.markTopicAsCompleted(studentId, topicId);

        assertTrue(topic.isCompleted());
        verify(topicRepo).save(topic);
        verify(studentRepo).save(student);
    }

    @Test
    void testMarkQuizAsCompleted() {
        Long studentId = 1L;
        Long quizId = 5L;

        Student student = new Student();
        student.setCompletedQuizzes(new HashSet<>());
        student.setTotalQuizzesAttempted(0);

        Quiz quiz = new Quiz();
        quiz.setTitle("Spring Boot");
        quiz.setAttempted(false);

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(student));
        when(quizRepo.findByIdAndStudent(quizId, student)).thenReturn(Optional.of(quiz));

        studentService.markQuizAsCompleted(studentId, quizId);

        assertTrue(quiz.isAttempted());
        assertEquals(1, student.getTotalQuizzesAttempted());
        assertTrue(student.getCompletedQuizzes().contains(quiz));

        verify(quizRepo).save(quiz);
        verify(studentRepo).save(student);
    }
}
