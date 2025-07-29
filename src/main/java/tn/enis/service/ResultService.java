package tn.enis.service;


import org.springframework.stereotype.Service;
import tn.enis.models.Result;
import tn.enis.models.Topic;
import tn.enis.repo.ResultRepository;
import tn.enis.repo.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {


    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Result createResult(Result result) {

        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {

        return resultRepository.findAll();
    }

    public Optional<Result> getResultById(Long id) {

        return resultRepository.findById(id);
    }

    public List<Result> getResultByUserId(Long userId)
    {
        return resultRepository.findByUserId(userId);
    }

    public boolean topicExists(String quizResult) {
        return resultRepository.findByQuizResult(quizResult).isPresent();
    }

    public Result save(Result result) {
        return resultRepository.save(result);
    }

}
