package tn.enis.dto;

public class ResultDTO {
    private Long id;
    private String quizResult;
    private Long userId;

    public ResultDTO() {}

    public ResultDTO(Long id, String quizResult, Long userId) {
        this.id = id;
        this.quizResult = quizResult;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getQuizResult() { return quizResult; }
    public void setQuizResult(String quizResult) { this.quizResult = quizResult; }
}
