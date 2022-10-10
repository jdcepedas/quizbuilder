package com.quizbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "attempts")
@Entity(name= "attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Quiz quiz;

    @Column(precision = 5, scale = 2)
    private Double totalScore;

    @OneToMany(cascade=CascadeType.ALL)
    private List<AttemptAnswer> answers;
}
