package com.shaanveer.placementportal.controller;

import com.shaanveer.placementportal.model.Topic;
import com.shaanveer.placementportal.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepo;

    // ✅ Create a single topic
    @PostMapping
    public Topic createTopic(@RequestBody Topic topic) {
        return topicRepo.save(topic);
    }

    // ✅ Bulk create topics
    @PostMapping("/bulk")
    public List<Topic> createTopics(@RequestBody List<Topic> topics) {
        return topicRepo.saveAll(topics);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepo.findAll();
    }

    @PutMapping("/{id}")
    public Topic updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        return topicRepo.findById(id).map(topic -> {
            topic.setName(updatedTopic.getName());
            topic.setCompleted(updatedTopic.isCompleted());
            return topicRepo.save(topic);
        }).orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable Long id) {
        topicRepo.deleteById(id);
    }
}
