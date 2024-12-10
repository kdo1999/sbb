package com.sbb.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sbb.entity.Answer;
import com.sbb.entity.Question;
import com.sbb.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);

		answerRepository.save(answer);
    }
}