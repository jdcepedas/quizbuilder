package com.quizbuilder.repository;

import com.quizbuilder.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

    Option findOptionById(Long id);
}
