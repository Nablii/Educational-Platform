package tn.enis.mapper;

import tn.enis.models.Topic;
import tn.enis.dto.TopicDTO;

public class TopicMapper {

    public static TopicDTO toDto(Topic topic) {
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setTopicName(topic.getTopicName());
        return dto;
    }
}
