package com.quizbuilder.dto;

import com.quizbuilder.enums.QuestionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionDTO {

    private Long id;

    private String statement;

    private QuestionTypeEnum type;

    private List<OptionDTO> options;
}