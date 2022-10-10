package com.quizbuilder.service.impl;

import com.quizbuilder.dto.QuizDTO;
import com.quizbuilder.enums.QuestionTypeEnum;
import com.quizbuilder.model.Option;
import com.quizbuilder.model.Quiz;
import com.quizbuilder.model.User;
import com.quizbuilder.repository.OptionRepository;
import com.quizbuilder.repository.QuizRepository;
import com.quizbuilder.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static com.quizbuilder.util.TestUtil.*;

@RunWith(MockitoJUnitRunner.class)
public class QuizBuilderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OptionRepository optionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private User user;

    @Mock
    private SecurityContext context;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private QuizBuilderServiceImpl quizBuilderService;

    @Before
    public void setup() {
        SecurityContextHolder.setContext(context);
    }

    @Test
    public void testCreateQuizWithSingleAnswerQuestion(){
        QuizDTO quizDTO  = getQuizDTO(QuestionTypeEnum.SINGLE_ANSWER, false);

        when(context.getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("username@mail.com");
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(modelMapper.map(eq(getCorrectOptionDTO("Option 1")), eq(Option.class)))
                .thenReturn(getCorrectOption(1L, "Option 1"));
        when(modelMapper.map(eq(getIncorrectOptionDTO("Option 2")), eq(Option.class)))
                .thenReturn(getIncorrectOption(2L, "Option 2"));

        when(optionRepository.saveAll(any()))
                .thenReturn(List.of(getCorrectOption(1L, "Option 1"),
                        getIncorrectOption(2L, "Option 2")));

        when(quizRepository.save(any()))
                .thenReturn(getQuiz(QuestionTypeEnum.SINGLE_ANSWER, false));

        Quiz quiz = quizBuilderService.createQuiz(quizDTO);

        assertEquals(quizDTO.getTitle(), quiz.getTitle());
        assertEquals(quizDTO.getPublished(), quiz.getPublished());
        assertEquals(1, quiz.getQuestions().size());
        assertEquals("Statement", quiz.getQuestions().get(0).getStatement());
        assertEquals(QuestionTypeEnum.SINGLE_ANSWER, quiz.getQuestions().get(0).getType());
        assertEquals(2, quiz.getQuestions().get(0).getOptions().size());
    }

    @Test
    public void testCreateQuizWithSingleAnswerQuestion_throwsExceptionWithNoCorrectOption(){
        QuizDTO quizDTO  = getQuizDTO(QuestionTypeEnum.SINGLE_ANSWER, false);

        when(context.getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("username@mail.com");
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(modelMapper.map(eq(getCorrectOptionDTO("Option 1")), eq(Option.class)))
                .thenReturn(getIncorrectOption(1L, "Option 1"));
        when(modelMapper.map(eq(getIncorrectOptionDTO("Option 2")), eq(Option.class)))
                .thenReturn(getIncorrectOption(2L, "Option 2"));

        assertThrows(RuntimeException.class, () -> quizBuilderService.createQuiz(quizDTO));
    }

    @Test
    public void testCreateQuizWithSingleAnswerQuestion_throwsExceptionWithTwoCorrectOptions(){
        QuizDTO quizDTO  = getQuizDTO(QuestionTypeEnum.SINGLE_ANSWER, false);

        when(context.getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("username@mail.com");
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(modelMapper.map(eq(getCorrectOptionDTO("Option 1")), eq(Option.class)))
                .thenReturn(getCorrectOption(1L,"Option 1"));
        when(modelMapper.map(eq(getIncorrectOptionDTO("Option 2")), eq(Option.class)))
                .thenReturn(getCorrectOption(2L, "Option 2"));

        assertThrows(RuntimeException.class, () -> quizBuilderService.createQuiz(quizDTO));
    }

    @Test
    public void testCreateQuizWithMultipleAnswerQuestion(){
        QuizDTO quizDTO  = getQuizDTO(QuestionTypeEnum.MULTIPLE_ANSWER, false);

        when(context.getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("username@mail.com");
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        when(modelMapper.map(eq(getCorrectOptionDTO("Option 1")), eq(Option.class)))
                .thenReturn(getCorrectOption(1L, "Option 1"));
        when(modelMapper.map(eq(getIncorrectOptionDTO("Option 2")), eq(Option.class)))
                .thenReturn(getIncorrectOption(2L, "Option 2"));
        when(modelMapper.map(eq(getIncorrectOptionDTO("Option 3")), eq(Option.class)))
                .thenReturn(getIncorrectOption(3L, "Option 3"));
        when(modelMapper.map(eq(getIncorrectOptionDTO("Option 4")), eq(Option.class)))
                .thenReturn(getIncorrectOption(4L, "Option 4"));

        when(optionRepository.saveAll(any()))
                .thenReturn(List.of(
                        getCorrectOption(1L, "Option 1"),
                        getIncorrectOption(2L, "Option 2"),
                        getIncorrectOption(3L, "Option 3"),
                        getIncorrectOption(4L, "Option 4")));

        when(quizRepository.save(any()))
                .thenReturn(getQuiz(QuestionTypeEnum.MULTIPLE_ANSWER, false));

        Quiz quiz = quizBuilderService.createQuiz(quizDTO);

        assertEquals(quizDTO.getTitle(), quiz.getTitle());
        assertEquals(quizDTO.getPublished(), quiz.getPublished());
        assertEquals(1, quiz.getQuestions().size());
        assertEquals("Statement", quiz.getQuestions().get(0).getStatement());
        assertEquals(QuestionTypeEnum.MULTIPLE_ANSWER, quiz.getQuestions().get(0).getType());
        assertEquals(4, quiz.getQuestions().get(0).getOptions().size());
    }
}
