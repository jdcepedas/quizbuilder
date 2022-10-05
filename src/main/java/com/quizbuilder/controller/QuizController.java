package com.quizbuilder.controller;

import com.quizbuilder.dto.AttemptDTO;
import com.quizbuilder.dto.QuizDTO;
import com.quizbuilder.model.Quiz;
import com.quizbuilder.service.QuizBuilderService;
import com.quizbuilder.service.QuizTakerService;
import com.quizbuilder.model.Attempt;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    @Autowired
    private final QuizBuilderService quizBuilderService;

    @Autowired
    private final QuizTakerService quizTakerService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/createQuiz")
    @PreAuthorize("hasAuthority('SCOPE_BUILDER')")
    public QuizDTO createQuiz(@RequestBody QuizDTO quizDTO){

        Quiz quiz = quizBuilderService.createQuiz(quizDTO);
        return modelMapper.map(quiz, QuizDTO.class);

    }

    @PostMapping("/publishQuiz/{quizId}")
    @PreAuthorize("hasAuthority('SCOPE_BUILDER')")
    public ResponseEntity<String> publishQuiz(@PathVariable ("quizId") Long quizId) {

        quizBuilderService.publishQuiz(quizId);
        return new ResponseEntity<>("Quiz " + quizId + " published", HttpStatus.OK);
    }

    @PostMapping("/attemptQuiz/{quizId}")
    @PreAuthorize("hasAuthority('SCOPE_TAKER')")
    public AttemptDTO attemptQuiz(@PathVariable Long quizId,
                                  @RequestBody AttemptDTO attemptDTO) {
        Attempt attempt = quizTakerService.attemptQuiz(quizId, attemptDTO);
        return modelMapper.map(attempt, AttemptDTO.class);

    }

    @GetMapping("/attemptQuiz/{attemptId}/totalScore")
    @PreAuthorize("hasAuthority('SCOPE_TAKER')")
    public ResponseEntity<String> attemptTotalScore(@PathVariable Long attemptId) {

        Double score = quizTakerService.getAttemptScore(attemptId);
        return new ResponseEntity<>(String.valueOf(score), HttpStatus.OK);
    }
}
