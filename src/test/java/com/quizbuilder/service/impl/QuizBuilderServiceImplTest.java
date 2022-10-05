package com.quizbuilder.service.impl;

import com.quizbuilder.dto.OptionDTO;
import com.quizbuilder.dto.QuestionAnswerDTO;
import com.quizbuilder.dto.QuestionDTO;
import com.quizbuilder.dto.QuizDTO;
import com.quizbuilder.model.Quiz;
import com.quizbuilder.repository.QuizRepository;
import com.quizbuilder.service.QuizBuilderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

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
                .correctOptions(getCorrectQuestionOptions())
                .incorrectOptions(getIncorrectQuestionOptions())
                .statement("Statement")
                .type("SINGLE_ANSWER")
                .build();

        return questionDTO;
    }

    public List<OptionDTO> getCorrectQuestionOptions() {
        OptionDTO option1 = OptionDTO.builder()
                .optionId(1l)
                .optionText("Option 1")
                .build();

        return List.of(option1);
    }

    public List<OptionDTO> getIncorrectQuestionOptions() {
        OptionDTO option2 = OptionDTO.builder()
                .optionId(2l)
                .optionText("Option 2")
                .build();

        return List.of(option2);
    }
}
