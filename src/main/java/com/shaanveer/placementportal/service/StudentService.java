package com.shaanveer.placementportal.service;

public interface StudentService {
    void markTopicAsCompleted(Long studentId, Long topicId);
    void markQuizAsCompleted(Long studentId, Long quizId);
}
