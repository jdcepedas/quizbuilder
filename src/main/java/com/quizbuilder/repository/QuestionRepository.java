package com.quizbuilder.repository;

import com.quizbuilder.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findQuestionById(Long id);
}
