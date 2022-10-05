package com.quizbuilder.repository;

import com.quizbuilder.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Attempt findAttemptByQuizId(Long id);

    @Query(value= "select * from attempts where user_id=:id and quiz_id=:quizId", nativeQuery = true)
    Attempt findAttemptByUserIdAndQuizId(@Param("id") Long id, @Param("quizId") Long quizId);
}
