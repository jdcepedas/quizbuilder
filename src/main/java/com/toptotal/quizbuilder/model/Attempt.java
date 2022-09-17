package com.toptotal.quizbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "options")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attempt_id")
    private Long id;

    @ManyToOne
    private Quiz quiz;

    private Double totalScore;

    @OneToMany
    private List<AttemptAnswer> answers;
}
