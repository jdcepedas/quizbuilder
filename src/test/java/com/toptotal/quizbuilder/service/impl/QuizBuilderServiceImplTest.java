package com.toptotal.quizbuilder.service.impl;

import com.toptotal.quizbuilder.dto.*;
import com.toptotal.quizbuilder.model.Quiz;
import com.toptotal.quizbuilder.repository.QuizRepository;
import com.toptotal.quizbuilder.service.QuizBuilderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class QuizBuilderServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizBuilderService quizBuilderService;

    @Test
    public void testCreateQuiz(){
        QuizDTO quizDTO  = getQuizDTO();

        Quiz quiz = quizBuilderService.createQuiz(quizDTO);

        assertEquals(quiz.getTitle(), quizDTO.getTitle());
    }

    public QuizDTO getQuizDTO() {
        QuizDTO quizDTO = QuizDTO
                .builder()
                .id(1L)
                .published(false)
                .title("Title")
                .questions(List.of(getQuestionDTO()))
                .build();
        return quizDTO;
    }

    public QuestionDTO getQuestionDTO(){
        QuestionDTO questionDTO = QuestionDTO
                .builder()
                .id(1L)
                .options(getQuestionOptions())
                .answer(getQuestionAnswer())
                .statement("Statement")
                .type("SINGLE_ANSWER")
                .build();

        return questionDTO;
    }

    public QuestionAnswerDTO getQuestionAnswer() {
        QuestionAnswerDTO answer = QuestionAnswerDTO
                .builder()
                .options(List.of(OptionDTO.builder()
                                .optionId(1l)
                                .optionText("Option 1")
                        .build()))
                .build();

        return answer;
    }

    public Set<OptionDTO> getQuestionOptions() {
        OptionDTO option1 = OptionDTO.builder()
                .optionId(1l)
                .optionText("Option 1")
                .build();

        OptionDTO option2 = OptionDTO.builder()
                .optionId(2l)
                .optionText("Option 2")
                .build();

        return Set.of(option1,option2);
    }
}
