package tn.enis.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.enis.models.Result;
import tn.enis.models.Topic;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByUserId(Long userId);
    Optional<Topic> findByQuizResult(String quizResult);
}