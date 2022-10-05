package com.quizbuilder.service.impl;

import com.quizbuilder.dto.AttemptAnswerDTO;
import com.quizbuilder.dto.AttemptDTO;
import com.quizbuilder.enums.QuestionTypeEnum;
import com.quizbuilder.model.*;
import com.quizbuilder.repository.QuestionRepository;
import com.quizbuilder.repository.QuizRepository;
import com.quizbuilder.service.QuizTakerService;
import com.quizbuilder.dto.OptionDTO;
import com.quizbuilder.repository.AttemptRepository;
import com.quizbuilder.repository.OptionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuizTakerServiceImplTest {

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private QuizTakerService quizTakerService;

    @Test
    public void testAttemptQuiz(){
        AttemptDTO attemptDTO = getAttemptDTO();

        when(attemptRepository.findAttemptByQuizId(any()))
                .thenReturn(null);

        when(quizRepository.findQuizById(any()))
                .thenReturn(getQuiz());

        when(questionRepository.findQuestionById(any()))
                .thenReturn(getQuizQuestions().get(0));

        when(optionRepository.findOptionById(1l))
                .thenReturn(Option.builder().id(1l).optionText("Option 1").build());

        Long quizId = 1L;
        Attempt attempt = quizTakerService.attemptQuiz(quizId, attemptDTO);

        assertEquals(Double.valueOf(100), attempt.getTotalScore());
    }

    public AttemptDTO getAttemptDTO() {
        AttemptDTO attemptDTO = AttemptDTO
                .builder()
                .quizId(1L)
                .id(1L)
                .answers(List.of(getAttemptAnswerDTO()))
                .build();

        return attemptDTO;
    }

    public AttemptAnswerDTO getAttemptAnswerDTO() {
        AttemptAnswerDTO answerDTO = AttemptAnswerDTO
                .builder()
                .options(getQuestionOptionsDTO())
                .build();

        return answerDTO;
    }

    public List<OptionDTO> getQuestionOptionsDTO() {
        OptionDTO option1 = OptionDTO.builder()
                .optionId(1l)
                .optionText("Option 1")
                .build();

        return List.of(option1);
    }

    public Quiz getQuiz() {

        return Quiz.builder()
                .id(1l)
                .published(true)
                .questions(List.of())
                .title("Title")
                .build();
    }

    public List<Question> getQuizQuestions() {

        return List.of(
                Question
                        .builder()
                        .type(QuestionTypeEnum.SINGLE_ANSWER)
                        .statement("Statement")
                        .options(getQuestionOptions())
                        .answer(getQuestionAnswer())
                        .build()
        );
    }

    public QuestionAnswer getQuestionAnswer(){
        return  QuestionAnswer
                .builder()
                .id(1l)
                .options(Set.of(
                        Option.builder()
                                .id(1l)
                                .optionText("Option 1")
                                .build()
                ))
                .build();
    }
    public Set<Option> getQuestionOptions() {
        Option option1 = Option.builder()
                .id(1l)
                .optionText("Option 1")
                .build();

        Option option2 = Option.builder()
                .id(2l)
                .optionText("Option 2")
                .build();

        return Set.of(option1,option2);
    }
}
