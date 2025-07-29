package tn.enis.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.models.Topic;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTopicName(String topicName);
}