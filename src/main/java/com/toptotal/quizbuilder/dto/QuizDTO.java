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
public class QuizDTO {

    private Long id;

    private String title;

    private List<QuestionDTO> questions;

    private Boolean published;
}
