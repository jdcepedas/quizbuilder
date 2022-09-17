package com.toptotal.quizbuilder.service.impl;

import com.toptotal.quizbuilder.dto.*;
import com.toptotal.quizbuilder.enums.QuestionTypeEnum;
import com.toptotal.quizbuilder.model.*;
import com.toptotal.quizbuilder.repository.QuizRepository;
import com.toptotal.quizbuilder.service.QuizBuilderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class QuizBuilderServiceImpl implements QuizBuilderService {

    @Autowired
    private final QuizRepository quizRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public Quiz createQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        List<Question> questions = new ArrayList<>();
        quiz.setQuestions(questions);
        quiz.setTitle(quizDTO.getTitle());
        quiz.setPublished(quizDTO.getPublished());

        for(QuestionDTO question: quizDTO.getQuestions()) {
            Set<OptionDTO> options = question.getOptions();
            Set<Option> newOptions = options.stream()
                    .map(option -> modelMapper.map(option, Option.class))
                    .collect(Collectors.toSet());

            QuestionAnswerDTO answerDTO = question.getAnswer();
            QuestionAnswer answer = QuestionAnswer.builder()
                    .options(answerDTO.getOptions().stream()
                            .map( option -> modelMapper.map(option, Option.class))
                            .collect(Collectors.toSet()))
                    .build();

            Question newQuestion = Question.builder()
                    .answer(answer)
                    .statement(question.getStatement())
                    .type(QuestionTypeEnum.valueOf(question.getType()))
                    .options(newOptions)
                    .build();

            questions.add(newQuestion);
        }

        Quiz newQuiz = quizRepository.save(quiz);

        return newQuiz;
    }

    public void publishQuiz(Long quizId) {
        Quiz quiz = quizRepository.findQuizById(quizId);
        quiz.setPublished(true);
    }

    public Quiz updateQuiz(QuizDTO quizDTO) {
        Quiz quiz = quizRepository.findQuizById(quizDTO.getId());

        if(quiz.getPublished()) {
            throw new RuntimeException("Quiz already published");
        }

        List<Question> questions = new ArrayList<>();
        quiz.setQuestions(questions);
        quiz.setTitle(quizDTO.getTitle());
        quiz.setPublished(quizDTO.getPublished());

        for(QuestionDTO question: quizDTO.getQuestions()) {
            Set<OptionDTO> options = question.getOptions();
            Set<Option> newOptions = options.stream()
                    .map(option -> modelMapper.map(option, Option.class))
                    .collect(Collectors.toSet());

            QuestionAnswerDTO answerDTO = question.getAnswer();
            QuestionAnswer answer = QuestionAnswer.builder()
                    .options(answerDTO.getOptions().stream()
                            .map( option -> modelMapper.map(option, Option.class))
                            .collect(Collectors.toSet()))
                    .build();

            Question newQuestion = Question.builder()
                    .answer(answer)
                    .statement(question.getStatement())
                    .type(QuestionTypeEnum.valueOf(question.getType()))
                    .options(newOptions)
                    .build();

            questions.add(newQuestion);
        }

        return quiz;
    }

    @Override
    public void deleteQuiz(Long quizId) {
        Quiz quiz = quizRepository.findQuizById(quizId);
        quizRepository.delete(quiz);
    }
}
