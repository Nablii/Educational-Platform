package tn.enis.dto;

public class TopicDTO {
    private Long id;
    private String topicName;

    public TopicDTO() {}

    public TopicDTO(Long id, String topicName) {
        this.id = id;
        this.topicName = topicName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }
}
