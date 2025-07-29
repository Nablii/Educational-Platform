package tn.enis.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.enis.models.Topic;
import tn.enis.dto.TopicDTO;
import tn.enis.mapper.TopicMapper;
import tn.enis.service.FastAPIService;
import tn.enis.service.TopicService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;
    private final FastAPIService fastAPIService;

    public TopicController(TopicService topicService, FastAPIService fastAPIService) {
        this.topicService = topicService;
        this.fastAPIService = fastAPIService;
    }

    @PostMapping
    public TopicDTO createTopic(@RequestBody Topic topic) {

        Topic savedTopic = topicService.createTopic(topic);
        return TopicMapper.toDto(savedTopic);
    }

    @GetMapping
    public List<Topic> getAllTopics() {

        return topicService.getAllTopics();
    }

    @GetMapping("/{id}")
    public Optional<Topic> getTopicById(@PathVariable Long id) {

        return topicService.getTopicById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> registerTopic(@RequestBody TopicDTO topicDTO) {
        String topicName = topicDTO.getTopicName();

        if (topicService.topicExists(topicName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Topic already exists");
        }

        Topic topic = new Topic();
        topic.setTopicName(topicName);

        Topic savedTopic = topicService.save(topic);

        return ResponseEntity.status(HttpStatus.CREATED).body("Topic created");
    }

    @PostMapping("/definition")
    public ResponseEntity<?> defineTopic(@RequestBody TopicDTO topicDTO) {
        String topicName = topicDTO.getTopicName();

        if(!topicService.topicExists(topicName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Topic does not exist");
        }

        String definition;
        try {
            definition = fastAPIService.queryFastApi(topicName, "Define this topic the topic is ");
        } catch (Exception exception)
        {
            definition = "Something went wrong";
        }

        // You could save this definition, log it, or return it
        return ResponseEntity.status(HttpStatus.CREATED).body("Definition: " + definition);
    }


    @PostMapping("/summary")
    public ResponseEntity<?> summarizeTopic(@RequestBody TopicDTO topicDTO) {
        String topicName = topicDTO.getTopicName();

        if(!topicService.topicExists(topicName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Topic does not exist");
        }

        String summary;
        try {
            summary = fastAPIService.queryFastApi(topicName, "Summarize this topic in the shortest paragraph possible the topic is  ");
        } catch (Exception exception)
        {
            summary = "Something went wrong";
        }

        // You could save this definition, log it, or return it
        return ResponseEntity.status(HttpStatus.CREATED).body("Summary: " + summary);
    }
}
