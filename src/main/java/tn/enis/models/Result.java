package tn.enis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "result", schema="public")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quiz_result")
    private String quizResult;
    @Column(name = "user_id")
    private Long userId;

    public Result() {}

    public Result(String quizResult, Long id, Long userId) {
        this.id = id;
        this.quizResult = quizResult;
        this.userId = userId;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.userId = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getQuizResult() { return quizResult; }
    public void setQuizResult(String quizResult) { this.quizResult = quizResult; }


}
