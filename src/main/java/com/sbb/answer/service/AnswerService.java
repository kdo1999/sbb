package com.sbb.answer.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sbb.infrastructure.answer.entity.Answer;
import com.sbb.infrastructure.question.entity.Question;
import com.sbb.infrastructure.answer.repository.AnswerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreatedAt(LocalDateTime.now());
        answer.setQuestion(question);

		answerRepository.save(answer);
    }
}