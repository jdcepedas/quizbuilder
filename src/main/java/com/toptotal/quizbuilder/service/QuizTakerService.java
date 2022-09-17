package com.toptotal.quizbuilder.service;

import com.toptotal.quizbuilder.dto.AttemptDTO;
import com.toptotal.quizbuilder.model.Attempt;

import java.util.Map;

public interface QuizTakerService {

    Attempt attemptQuiz(Long quizId, AttemptDTO attempt);

    Double getAttemptScore(Long attemptId);

    Map<Long, Double> getAnswersScores(Long attemptId);
}
