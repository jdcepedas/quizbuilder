package com.quizbuilder.service.impl;

import com.quizbuilder.dto.AttemptAnswerDTO;
import com.quizbuilder.dto.AttemptDTO;
import com.quizbuilder.enums.QuestionTypeEnum;
import org.mockito.stubbing.Answer;
import com.quizbuilder.model.*;
import com.quizbuilder.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import static com.quizbuilder.util.TestUtil.*;

@RunWith(MockitoJUnitRunner.class)
public class QuizTakerServiceImplTest {

    @Mock
    private UserRepository userRepository;

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

    @Mock
    private User user;

    @Mock
    private SecurityContext context;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private QuizTakerServiceImpl quizTakerService;

    @Before
    public void setup() {
        SecurityContextHolder.setContext(context);

        when(context.getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("username@mail.com");
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));
    }

    @Test
    public void testAttemptQuiz_withOneQuestion_SingleAnswer_100Score(){
        AttemptAnswerDTO attemptAnswerDTO = getAttemptAnswerDTO(1L);
        AttemptDTO attemptDTO = getAttemptDTO(List.of(attemptAnswerDTO));

        when(attemptRepository.findAttemptByUserIdAndQuizId(any(), any()))
                .thenReturn(null);

        when(quizRepository.findQuizById(any()))
                .thenReturn(getQuiz(QuestionTypeEnum.SINGLE_ANSWER, true));

        when(questionRepository.findQuestionById(any()))
                .thenReturn(getQuestion(QuestionTypeEnum.SINGLE_ANSWER));

        when(optionRepository.findOptionById(1L))
                .thenReturn(getCorrectOption(1L, "Option 1"));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Attempt attempt = (Attempt) invocation.getArguments()[0];
                attempt.setId(1L);
                return attempt;
            }
        }).when(attemptRepository).save(any());

        Long quizId = 1L;
        Attempt attempt = quizTakerService.attemptQuiz(quizId, attemptDTO);

        assertEquals(Double.valueOf(100), attempt.getTotalScore());
    }

    @Test
    public void testAttemptQuiz_withOneQuestion_SingleAnswer_0Score(){
        AttemptAnswerDTO attemptAnswerDTO = getAttemptAnswerDTO(2L);
        AttemptDTO attemptDTO = getAttemptDTO(List.of(attemptAnswerDTO));

        when(attemptRepository.findAttemptByUserIdAndQuizId(any(), any()))
                .thenReturn(null);

        when(quizRepository.findQuizById(any()))
                .thenReturn(getQuiz(QuestionTypeEnum.SINGLE_ANSWER, true));

        when(questionRepository.findQuestionById(any()))
                .thenReturn(getQuestion(QuestionTypeEnum.SINGLE_ANSWER));
        
        when(optionRepository.findOptionById(2L))
                .thenReturn(getIncorrectOption(2L, "Option 2"));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Attempt attempt = (Attempt) invocation.getArguments()[0];
                attempt.setId(1L);
                return attempt;
            }
        }).when(attemptRepository).save(any());

        Long quizId = 1L;
        Attempt attempt = quizTakerService.attemptQuiz(quizId, attemptDTO);

        assertEquals(Double.valueOf(-100), attempt.getTotalScore());
    }

    @Test
    public void testAttemptQuiz_withOneQuestion_MultipleAnswer_33Score(){
        AttemptAnswerDTO attemptAnswerDTO = getAttemptAnswerDTO(1L, 2L);
        AttemptDTO attemptDTO = getAttemptDTO(List.of(attemptAnswerDTO));

        when(attemptRepository.findAttemptByUserIdAndQuizId(any(), any()))
                .thenReturn(null);

        when(quizRepository.findQuizById(any()))
                .thenReturn(getQuiz(QuestionTypeEnum.MULTIPLE_ANSWER, true));

        when(questionRepository.findQuestionById(any()))
                .thenReturn(getQuestion(QuestionTypeEnum.MULTIPLE_ANSWER));

        when(optionRepository.findOptionById(1L))
                .thenReturn(getCorrectOption(1L, "Option 1"));
        when(optionRepository.findOptionById(2L))
                .thenReturn(getIncorrectOption(2L, "Option 2"));

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Attempt attempt = (Attempt) invocation.getArguments()[0];
                attempt.setId(1L);
                return attempt;
            }
        }).when(attemptRepository).save(any());

        Long quizId = 1L;
        Attempt attempt = quizTakerService.attemptQuiz(quizId, attemptDTO);

        assertEquals(Double.valueOf(66.66), attempt.getTotalScore(), 0.01);
    }
}
