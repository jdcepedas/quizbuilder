package com.quizbuilder.service.impl;

import com.quizbuilder.enums.OptionStatusEnum;
import com.quizbuilder.model.*;
import com.quizbuilder.repository.*;
import com.quizbuilder.service.QuizTakerService;
import com.quizbuilder.dto.AttemptAnswerDTO;
import com.quizbuilder.dto.AttemptDTO;
import com.quizbuilder.enums.QuestionTypeEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class QuizTakerServiceImpl implements QuizTakerService {

    @Autowired
    private final AttemptRepository attemptRepository;

    @Autowired
    private final QuizRepository quizRepository;

    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private final OptionRepository optionRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public Attempt attemptQuiz(Long quizId, AttemptDTO attempt) {
        log.info("Verifying if there is an existing attempt");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Current user not found in the database"));

        Attempt existingAttempt = attemptRepository.findAttemptByUserIdAndQuizId(user.getId(), quizId);
        if(existingAttempt != null) {
            throw new RuntimeException("Existing attempt for this quiz");
        }

        Quiz quiz = quizRepository.findQuizById(quizId);

        if(!quiz.getPublished()){
            throw new RuntimeException("Quiz is not yet published!");
        }

        List<AttemptAnswerDTO> answerList = attempt.getAnswers();

        Double totalScore = 0d;

        Attempt newAttempt = new Attempt();
        newAttempt.setQuiz(quiz);
        newAttempt.setAnswers(new ArrayList<>());

        Integer numberOfQuestions = quiz.getQuestions().size();

        for(AttemptAnswerDTO answer: answerList) {
            Question question = questionRepository.findQuestionById(answer.getQuestionId());
            Integer allOptionsSize = question.getOptions().size();

            Set<Option> correctOptions = question
                    .getOptions()
                    .stream()
                    .filter(option ->
                            option.getStatus().equals(OptionStatusEnum.CORRECT))
                    .collect(Collectors.toSet());

            Set<Option> selectedOptions =
            answer.getOptions().stream().map(option -> {
                        return optionRepository.findOptionById(option.getOptionId());
                    }).collect(Collectors.toSet());
            Double answerScore = 0d;
            Double correctWeight;
            Double incorrectWeight;
            if(question.getType().equals(QuestionTypeEnum.SINGLE_ANSWER)) {
                correctWeight = 1d;
                incorrectWeight = -1d;
            } else if(question.getType().equals(QuestionTypeEnum.MULTIPLE_ANSWER))  {
                correctWeight = 1d/correctOptions.size();
                incorrectWeight = -1d/(allOptionsSize-correctOptions.size());
            } else {
                throw new RuntimeException("Not supported question type");
            }

            if(!selectedOptions.isEmpty()) {
                for (Option option : selectedOptions) {
                    if(correctOptions.contains(option)) {
                        answerScore += correctWeight;
                    } else {
                        answerScore += incorrectWeight;
                    }
                }
            }

            AttemptAnswer answerToSave = new AttemptAnswer();
            answerToSave.setOptions(selectedOptions);
            answerToSave.setQuestion(question);
            answerToSave.setIndividualScore(answerScore);

            newAttempt.getAnswers().add(answerToSave);

            totalScore += answerScore/numberOfQuestions;
        }

        // Save the score as a percentage
        newAttempt.setTotalScore(totalScore*100);

        Attempt savedAttempt = attemptRepository.save(newAttempt);

        List<Attempt> attempts = user.getAttempts();
        if(attempts == null) {
            attempts = new ArrayList<>();
        }
        attempts.add(savedAttempt);
        userRepository.save(user);

        return savedAttempt;
    }

    @Override
    public Double getAttemptScore(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId).orElseThrow( () ->
                new RuntimeException("Attempt not found")
        );

        return attempt.getTotalScore();
    }

    @Override
    public Map<Long, Double> getAnswersScores(Long attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId).orElseThrow( () ->
                new RuntimeException("Attempt not found")
        );

        List<AttemptAnswer> answers = attempt.getAnswers();

        Map<Long, Double> mapAnswerToScore = new LinkedHashMap<>();

        answers.stream().map( answer -> {
            mapAnswerToScore.put(answer.getId(), answer.getIndividualScore());
            return null;
        });

       return mapAnswerToScore;
    }
}
