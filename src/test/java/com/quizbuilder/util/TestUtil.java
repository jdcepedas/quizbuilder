package com.quizbuilder.util;

import com.quizbuilder.dto.*;
import com.quizbuilder.enums.OptionStatusEnum;
import com.quizbuilder.enums.QuestionTypeEnum;
import com.quizbuilder.model.Option;
import com.quizbuilder.model.Question;
import com.quizbuilder.model.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestUtil {

    public static QuizDTO getQuizDTO(QuestionTypeEnum type, Boolean published) {
        QuizDTO quizDTO = QuizDTO
                .builder()
                .id(1L)
                .published(published)
                .title("Title")
                .questions(List.of(getQuestionDTO(type)))
                .build();
        return quizDTO;
    }

    public static Quiz getQuiz(QuestionTypeEnum type, Boolean published) {
        Quiz quiz = Quiz
                .builder()
                .id(1L)
                .published(published)
                .title("Title")
                .questions(List.of(getQuestion(type)))
                .build();
        return quiz;
    }

    public static Quiz getQuiz(QuestionTypeEnum type, Boolean published, List<Question> questions) {
        Quiz quiz = Quiz
                .builder()
                .id(1L)
                .published(published)
                .title("Title")
                .questions(questions)
                .build();
        return quiz;
    }

    public static QuestionDTO getQuestionDTO(QuestionTypeEnum type){
        QuestionDTO questionDTO = QuestionDTO
                .builder()
                .options(getQuestionOptionsDTO(type))
                .statement("Statement")
                .type(type)
                .build();

        return questionDTO;
    }

    public static Question getQuestion(QuestionTypeEnum type) {
        Question question = Question
                .builder()
                .id(1L)
                .options(getQuestionOptions(type))
                .statement("Statement")
                .type(type)
                .build();

        return question;
    }

    public static Option getCorrectOption(Long id, String optionText) {
        return Option.builder()
                .id(id)
                .optionText(optionText)
                .status(OptionStatusEnum.CORRECT)
                .build();
    }

    public static Option getIncorrectOption(Long id, String optionText) {
        return Option.builder()
                .id(id)
                .optionText(optionText)
                .status(OptionStatusEnum.INCORRECT)
                .build();
    }

    public static OptionDTO getCorrectOptionDTO(String optionText) {
        return OptionDTO.builder()
                .optionText(optionText)
                .status(OptionStatusEnum.CORRECT)
                .build();
    }

    public static OptionDTO getIncorrectOptionDTO(String optionText) {
        return OptionDTO.builder()
                .optionText(optionText)
                .status(OptionStatusEnum.INCORRECT)
                .build();
    }

    public static Set<Option> getQuestionOptions(QuestionTypeEnum type) {
        Option option1 = getCorrectOption(1L,"Option 1");

        Option option2 = getIncorrectOption(2L,"Option 2");

        Option option3 = getIncorrectOption(3L,"Option 3");

        Option option4 = getIncorrectOption(4L,"Option 4");

        switch (type) {
            case SINGLE_ANSWER:
                return Set.of(option1, option2);
            case MULTIPLE_ANSWER:
                return Set.of(option1, option2, option3, option4);
            default:
                throw new IllegalArgumentException("Type not supported");
        }
    }

    public static List<OptionDTO> getQuestionOptionsDTO(QuestionTypeEnum type) {
        OptionDTO option1 = getCorrectOptionDTO("Option 1");

        OptionDTO option2 = getIncorrectOptionDTO("Option 2");

        switch (type) {
            case SINGLE_ANSWER:
                return List.of(option1, option2);
            case MULTIPLE_ANSWER:
                return List.of(option1, option2,
                        getIncorrectOptionDTO("Option 3"),
                        getIncorrectOptionDTO("Option 4"));
            default:
                throw new IllegalArgumentException("Type not supported");
        }
    }

    public static AttemptDTO getAttemptDTO(List<AttemptAnswerDTO> attemptAnswerDTOList) {
        AttemptDTO attemptDTO = AttemptDTO
                .builder()
                .quizId(1L)
                .id(1L)
                .answers(attemptAnswerDTOList)
                .build();

        return attemptDTO;
    }

    public static AttemptAnswerDTO getAttemptAnswerDTO(Long... optionIds) {
        AttemptAnswerDTO answerDTO = AttemptAnswerDTO
                .builder()
                .options(getQuestionOptionsDTO(optionIds))
                .build();

        return answerDTO;
    }

    public static List<OptionTakerDTO> getQuestionOptionsDTO(Long... optionIds) {
        List<OptionTakerDTO> list = new ArrayList<>();
        for(Long id: optionIds) {
            list.add(
                    OptionTakerDTO.builder()
                            .optionId(id)
                            .optionText("Option "+ id)
                            .build()
            );
        }
        return list;
    }
}
