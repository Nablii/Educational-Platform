package tn.enis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz", schema="public")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quiz_name")
    private String quizName;

    public Quiz() {}

    public Quiz(String quizName, Long id) {
        this.id = id;
        this.quizName = quizName;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuizName() { return quizName; }
    public void setQuizName(String topicName) { this.quizName = topicName; }


}
