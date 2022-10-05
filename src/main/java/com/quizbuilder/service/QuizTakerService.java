package com.quizbuilder.service;

import com.quizbuilder.dto.AttemptDTO;
import com.quizbuilder.model.Attempt;

import java.util.Map;

public interface QuizTakerService {

    Attempt attemptQuiz(Long quizId, AttemptDTO attempt);

    Double getAttemptScore(Long attemptId);

    Map<Long, Double> getAnswersScores(Long attemptId);
}
