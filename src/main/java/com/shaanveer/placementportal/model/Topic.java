package com.shaanveer.placementportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean completed;

    // Bi-directional many-to-many relationship handled from Student side
    @ManyToMany(mappedBy = "topics")
    @JsonIgnore
    private List<Student> studentsWhoCompleted = new ArrayList<>();
}
