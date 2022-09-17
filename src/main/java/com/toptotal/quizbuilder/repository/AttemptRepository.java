package com.toptotal.quizbuilder.repository;

import com.toptotal.quizbuilder.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Attempt findAttemptByQuizId(Long id);
}
