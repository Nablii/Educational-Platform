package tn.enis.dto;

public class QuizDTO {
    private Long id;
    private String quizName;

    public QuizDTO() {}

    public QuizDTO(Long id, String quizName) {
        this.id = id;
        this.quizName = quizName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuizName() { return quizName; }
    public void setQuizName(String quizName) { this.quizName = quizName; }
}
