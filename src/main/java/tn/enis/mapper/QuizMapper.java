package tn.enis.mapper;

import tn.enis.models.Quiz;
import tn.enis.dto.QuizDTO;

public class QuizMapper {

    public static QuizDTO toDto(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setQuizName(quiz.getQuizName());
        return dto;
    }
}
