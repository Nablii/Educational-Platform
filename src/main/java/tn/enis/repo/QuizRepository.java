package tn.enis.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.models.Quiz;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByQuizName(String quizName);
}