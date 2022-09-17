package com.toptotal.quizbuilder.repository;

import com.toptotal.quizbuilder.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Quiz findQuizById(Long id);
}
