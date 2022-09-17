package com.toptotal.quizbuilder.repository;

import com.toptotal.quizbuilder.model.AttemptAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptAnswerRepository extends JpaRepository<AttemptAnswer, Long> {
}
