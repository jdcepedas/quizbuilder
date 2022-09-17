package com.toptotal.quizbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttemptAnswerDTO {

    private Long questionId;

    private List<OptionDTO> options;
}
