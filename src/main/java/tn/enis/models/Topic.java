package tn.enis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "topic", schema="public")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topic_name")
    private String topicName;

    public Topic() {}

    public Topic(String topicName, Long id) {
        this.id = id;
        this.topicName = topicName;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }


}
