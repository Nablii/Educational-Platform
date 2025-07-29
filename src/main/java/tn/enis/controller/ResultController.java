package tn.enis.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.enis.dto.ResultDTO;
import tn.enis.mapper.ResultMapper;
import tn.enis.models.Result;
import tn.enis.models.Topic;
import tn.enis.dto.TopicDTO;
import tn.enis.mapper.TopicMapper;
import tn.enis.service.FastAPIService;
import tn.enis.service.ResultService;
import tn.enis.service.TopicService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;
    private final FastAPIService fastAPIService;

    public ResultController(ResultService resultService, FastAPIService fastAPIService) {
        this.resultService = resultService;
        this.fastAPIService = fastAPIService;
    }

    @PostMapping
    public ResultDTO createTopic(@RequestBody Result result) {

        Result savedResult = resultService.createResult(result);
        return ResultMapper.toDto(savedResult);
    }

    @GetMapping
    public List<Result> getAllResults() {

        return resultService.getAllResults();
    }

    @GetMapping("/{userId}")
    public List<Result> getAllUserResults(@PathVariable Long userId) {

        return resultService.getResultByUserId(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> registerTopic(@RequestBody ResultDTO resultDTO) {
        String quizResult = resultDTO.getQuizResult();
        Long userId = resultDTO.getUserId();

        Result result = new Result();
        result.setQuizResult(quizResult);
        result.setUserId(userId);

        Result savedResult = resultService.save(result);

        return ResponseEntity.status(HttpStatus.CREATED).body("Result created");
    }
}
