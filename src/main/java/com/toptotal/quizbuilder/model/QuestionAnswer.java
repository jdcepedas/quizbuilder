package com.toptotal.quizbuilder.model;

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
    @Column(name = "question_answer_id")
    protected Long id;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(	name = "question_answer_options",
            joinColumns = @JoinColumn(name = "question_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    protected Set<Option> options;
}
