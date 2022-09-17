package com.toptotal.quizbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table(name = "attempt_answer")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attempt_answer_id")
    protected Long id;

    @ManyToOne
    private Question question;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(	name = "attempt_answer_options",
            joinColumns = @JoinColumn(name = "attempt_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    protected Set<Option> options;

    private Double individualScore;
}
