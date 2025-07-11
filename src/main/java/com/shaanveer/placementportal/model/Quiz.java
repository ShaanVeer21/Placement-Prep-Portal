package com.shaanveer.placementportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String topic;
    private int totalQuestions;
    private LocalDate createdAt = LocalDate.now();

    // Bi-directional many-to-many relationship handled from Student side
    @ManyToMany(mappedBy = "completedQuizzes")
    @JsonIgnore
    private List<Student> studentsWhoCompleted = new ArrayList<>();
}
