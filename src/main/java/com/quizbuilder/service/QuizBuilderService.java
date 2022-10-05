package com.quizbuilder.service;

import com.quizbuilder.dto.QuizDTO;
import com.quizbuilder.model.Quiz;

public interface QuizBuilderService {

    Quiz createQuiz(QuizDTO quizDTO);

    Quiz updateQuiz(QuizDTO quizDTO);

    public void publishQuiz(Long quizId);
    void deleteQuiz(Long quizId);
}
