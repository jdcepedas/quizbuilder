package com.quizbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table(name = "question_answer")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToMany(fetch =  FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(	name = "question_answer_options",
            joinColumns = @JoinColumn(name = "question_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    protected Set<Option> options;
}
