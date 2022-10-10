package com.quizbuilder.model;

import com.quizbuilder.enums.OptionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "options")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String optionText;

    @Enumerated(EnumType.STRING)
    private OptionStatusEnum status;
}
