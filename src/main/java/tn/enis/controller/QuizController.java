package tn.enis.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.enis.models.Quiz;
import tn.enis.dto.QuizDTO;
import tn.enis.mapper.QuizMapper;
import tn.enis.service.FastAPIService;
import tn.enis.service.QuizService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final FastAPIService fastAPIService;

    public QuizController(QuizService quizService, FastAPIService fastAPIService) {
        this.quizService = quizService;
        this.fastAPIService = fastAPIService;
    }

    @PostMapping
    public QuizDTO createQuiz(@RequestBody Quiz quiz) {

        Quiz savedQuiz = quizService.createQuiz(quiz);
        return QuizMapper.toDto(savedQuiz);
    }

    @GetMapping
    public List<Quiz> getAllQuizes() {

        return quizService.getAllQuizes();
    }

    @GetMapping("/{id}")
    public Optional<Quiz> getQuizById(@PathVariable Long id) {

        return quizService.getQuizById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> registerQuiz(@RequestBody QuizDTO quizDTO) {
        String quizName = quizDTO.getQuizName();

        if (quizService.quizExists(quizName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Quiz already exists");
        }

        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);

        Quiz savedQuiz = quizService.save(quiz);

        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created");
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateQuiz(@RequestBody QuizDTO quizDTO) {
        String quizName = quizDTO.getQuizName();

        if(!quizService.quizExists(quizName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Quiz does not exist");
        }

        String quiz;
        try {
            quiz = fastAPIService.queryFastApi(quizName, "Generate a quiz of 5 questions about this topic and separate the questions by a linebreak no answers included just the questions from 1 to 5 so basically send only 5 lines ");
        } catch (Exception exception)
        {
            quiz = "Something went wrong";
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz:\n" + quiz);
    }

    @PostMapping("/correct")
    public ResponseEntity<?> correctQuiz(@RequestBody QuizDTO quizDTO) {
        String quizName = quizDTO.getQuizName();

        if (quizService.quizExists(quizName)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Quiz already exists");
        }

        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);

        Quiz savedQuiz = quizService.save(quiz);

        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created");
    }
}
