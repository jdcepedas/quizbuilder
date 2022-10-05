package com.quizbuilder.model;

import com.quizbuilder.enums.QuestionTypeEnum;
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
    private Long id;

    private String statement;

    private QuestionTypeEnum type;

    @ManyToMany(fetch =  FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(	name = "question_options",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options = new LinkedHashSet<>();

    @ManyToOne(cascade=CascadeType.PERSIST)
    private QuestionAnswer answer;
}
