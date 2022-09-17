package com.toptotal.quizbuilder.model;

import com.toptotal.quizbuilder.enums.QuestionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "questions")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    private static final Integer MAX_OPTIONS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Long id;

    private String statement;

    private QuestionTypeEnum type;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(	name = "question_options",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options = new LinkedHashSet<>();

    @ManyToOne
    private QuestionAnswer answer;
}
