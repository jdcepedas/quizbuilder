package com.quizbuilder.service.impl;

import com.quizbuilder.dto.OptionDTO;
import com.quizbuilder.dto.QuestionDTO;
import com.quizbuilder.dto.QuizDTO;
import com.quizbuilder.enums.OptionStatusEnum;
import com.quizbuilder.enums.QuestionTypeEnum;
import com.quizbuilder.model.*;
import com.quizbuilder.repository.OptionRepository;
import com.quizbuilder.repository.QuizRepository;
import com.quizbuilder.service.QuizBuilderService;
import com.quizbuilder.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class QuizBuilderServiceImpl implements QuizBuilderService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Quiz createQuiz(QuizDTO quizDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Current user not found in the database"));

        Quiz quiz = new Quiz();
        List<Question> questions = new ArrayList<>();
        quiz.setQuestions(questions);
        quiz.setTitle(quizDTO.getTitle());
        quiz.setPublished(quizDTO.getPublished());

        for(QuestionDTO question: quizDTO.getQuestions()) {
            List<OptionDTO> optionsDTO = question.getOptions();
            List<Option> options = optionsDTO.stream()
                    .map(option -> modelMapper.map(option, Option.class))
                    .collect(Collectors.toList());

            checkOptions(question.getType(), options);

            options = optionRepository.saveAll(options);

            Question newQuestion = Question.builder()
                    .statement(question.getStatement())
                    .type(question.getType())
                    .options(options.stream().collect(Collectors.toSet()))
                    .build();

            questions.add(newQuestion);
        }

        Quiz newQuiz = quizRepository.save(quiz);
        List<Quiz> quizzes = user.getQuizzes();
        if(quizzes != null) {
            quizzes.add(newQuiz);
        } else {
            quizzes = new ArrayList<>();
            quizzes.add(newQuiz);
        }
        userRepository.save(user);

        return newQuiz;
    }

    static void checkOptions(QuestionTypeEnum type, List<Option> options) {
        if(type.equals(QuestionTypeEnum.SINGLE_ANSWER)){
            List<Option> correctOption = options
                    .stream()
                    .filter(option -> option.getStatus() == OptionStatusEnum.CORRECT)
                    .collect(Collectors.toList());
            if(correctOption.size() != 1) {
                throw new RuntimeException("Question must have only one correct answer");
            }
        }
    }

    public void publishQuiz(Long quizId) {
        Quiz quiz = quizRepository.findQuizById(quizId);
        quiz.setPublished(true);
    }

    public Quiz updateQuiz(QuizDTO quizDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Current user not found in the database"));

        Quiz quiz = quizRepository.findQuizById(quizDTO.getId());

        if(quiz.getPublished()) {
            throw new RuntimeException("Quiz already published");
        }

        List<Question> questions = new ArrayList<>();
        quiz.setQuestions(questions);
        quiz.setTitle(quizDTO.getTitle());
        quiz.setPublished(quizDTO.getPublished());

        for(QuestionDTO question: quizDTO.getQuestions()) {
            List<OptionDTO> optionsDTO = question.getOptions();
            List<Option> options = optionsDTO.stream()
                    .map(option -> modelMapper.map(option, Option.class))
                    .collect(Collectors.toList());
            options = optionRepository.saveAll(options);

            Question newQuestion = Question.builder()
                    .statement(question.getStatement())
                    .type(question.getType())
                    .options(options.stream().collect(Collectors.toSet()))
                    .build();

            questions.add(newQuestion);
        }
        quiz.setQuestions(questions);

        List<Quiz> quizzes = user.getQuizzes();
        if(quizzes != null) {
            quizzes.add(quiz);
        } else {
            quizzes = new ArrayList<>();
            quizzes.add(quiz);
        }
        userRepository.save(user);

        return quiz;
    }

    @Override
    public void deleteQuiz(Long quizId) {
        Quiz quiz = quizRepository.findQuizById(quizId);
        quizRepository.delete(quiz);
    }
}
