package com.quizbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Table(name = "attempt_answer")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@AssociationOverride(
        name="options",
        joinTable=@JoinTable(
                name="attempt_answer_options",
                joinColumns=@JoinColumn(name="attempt_answer_id"),
                inverseJoinColumns=@JoinColumn(name="option_id")
        )
)
public class AttemptAnswer extends Answer {

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Question question;

    @Column(precision = 3, scale = 2)
    private Double individualScore;
}
