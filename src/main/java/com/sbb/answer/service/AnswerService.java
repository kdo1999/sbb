package com.sbb.answer.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.sbb.infrastructure.answer.entity.Answer;
import com.sbb.infrastructure.question.entity.Question;
import com.sbb.infrastructure.answer.repository.AnswerRepository;
import com.sbb.infrastructure.siteUser.entity.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreatedAt(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);

		answerRepository.save(answer);
    }
}