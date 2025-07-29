package tn.enis.service;


import org.springframework.stereotype.Service;
import tn.enis.models.Topic;
import tn.enis.repo.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {


    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic createTopic(Topic topic) {

        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {

        return topicRepository.findAll();
    }

    public Optional<Topic> getTopicById(Long id) {

        return topicRepository.findById(id);
    }

    public boolean topicExists(String topicName) {
        return topicRepository.findByTopicName(topicName).isPresent();
    }

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

}
