package com.shaanveer.placementportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String currentTopic;
    private int totalQuizzesAttempted;

    // 💡 List of topics the student is tracking
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;

    // ✅ Completed quizzes
    @ManyToMany
    @JoinTable(
        name = "student_completed_quizzes",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    @JsonIgnore // To avoid infinite loop
    private List<Quiz> completedQuizzes;

    // ✅ Completed topics (used by Topic entity)
    @ManyToMany
    @JoinTable(
        name = "student_completed_topics",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @JsonIgnore
    private List<Topic> completedTopics;
}
