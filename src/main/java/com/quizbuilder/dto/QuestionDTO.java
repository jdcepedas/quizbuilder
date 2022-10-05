package com.quizbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionDTO {

    private Long id;

    private String statement;

    private String type;

    private List<OptionDTO> correctOptions;

    private List<OptionDTO> incorrectOptions;
}