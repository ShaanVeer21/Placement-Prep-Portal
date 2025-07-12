package com.shaanveer.placementportal.repository;

import com.shaanveer.placementportal.model.Topic;
import com.shaanveer.placementportal.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByStudent(Student student);
    Optional<Topic> findByIdAndStudent(Long id, Student student);
}
