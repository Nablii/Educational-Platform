package tn.enis.service;


import org.springframework.stereotype.Service;
import tn.enis.models.Quiz;
import tn.enis.repo.QuizRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {


    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz(Quiz quiz) {

        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizes() {

        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {

        return quizRepository.findById(id);
    }

    public boolean quizExists(String quizName) {
        return quizRepository.findByQuizName(quizName).isPresent();
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

}
