package com.shaanveer.placementportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"completedQuizzes", "completedTopics", "personalTopics"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role = "USER";

    private String currentTopic;
    private int totalQuizzesAttempted;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id") // Unidirectional
    private Set<Topic> personalTopics = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "student_completed_quizzes",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    @JsonIgnore
    private Set<Quiz> completedQuizzes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "student_completed_topics",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @JsonIgnore
    private Set<Topic> completedTopics = new HashSet<>();
}
