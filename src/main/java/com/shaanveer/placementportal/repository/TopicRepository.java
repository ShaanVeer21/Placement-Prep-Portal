package com.shaanveer.placementportal.repository;

import com.shaanveer.placementportal.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
