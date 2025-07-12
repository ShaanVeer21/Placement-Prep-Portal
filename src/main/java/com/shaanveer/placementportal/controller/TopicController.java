package com.shaanveer.placementportal.controller;

import com.shaanveer.placementportal.model.Student;
import com.shaanveer.placementportal.model.Topic;
import com.shaanveer.placementportal.repository.StudentRepository;
import com.shaanveer.placementportal.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student getCurrentStudent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Student student) {
            return student;
        }

        throw new RuntimeException("Unauthenticated access");
    }


    @GetMapping
    public List<Topic> getAllTopics() {
        Student student = getCurrentStudent();
        return topicRepository.findByStudent(student);
    }

    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        Student student = getCurrentStudent();
        topic.setStudent(student);
        return topicRepository.save(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic topicDetails) {
        Student student = getCurrentStudent();
        Topic topic = topicRepository.findByIdAndStudent(id, student).orElseThrow();

        topic.setName(topicDetails.getName());
        topic.setCompleted(topicDetails.isCompleted());
        return ResponseEntity.ok(topicRepository.save(topic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Student student = getCurrentStudent();
        Topic topic = topicRepository.findByIdAndStudent(id, student).orElseThrow();

        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();
    }
}
