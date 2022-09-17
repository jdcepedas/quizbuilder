package com.toptotal.quizbuilder.service.impl;

import com.toptotal.quizbuilder.model.Question;
import com.toptotal.quizbuilder.repository.QuestionRepository;
import com.toptotal.quizbuilder.service.QuestionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }
}
