package com.toptotal.quizbuilder.repository;

import com.toptotal.quizbuilder.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findQuestionById(Long id);
}
