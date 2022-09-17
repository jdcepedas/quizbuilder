package com.toptotal.quizbuilder.service;

import com.toptotal.quizbuilder.dto.QuizDTO;
import com.toptotal.quizbuilder.model.Quiz;

public interface QuizBuilderService {

    Quiz createQuiz(QuizDTO quizDTO);

    Quiz updateQuiz(QuizDTO quizDTO);

    public void publishQuiz(Long quizId);
    void deleteQuiz(Long quizId);
}
