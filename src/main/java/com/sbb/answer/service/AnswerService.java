package com.sbb.answer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sbb.answer.domain.Answer;
import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.answer.repository.AnswerRepository;
import com.sbb.question.domain.Question;
import com.sbb.siteUser.domain.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;


    public Answer create(Question question, String content, SiteUser siteUser) {
        Answer createAnswer = Answer.builder()
            .content(content)
            .question(question)
            .createdAt(LocalDateTime.now())
            .question(question)
            .author(siteUser)
            .build();

        Answer savedAnswer = answerRepository.save(createAnswer);

        return savedAnswer;
    }

    public Answer getAnswer(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        Answer modifyAnswer = Answer.builder()
            .id(answer.id())
            .content(content)
            .author(answer.author())
            .createdAt(answer.createdAt())
            .modifiedAt(LocalDateTime.now())
            .voter(answer.voter())
            .question(answer.question())
            .build();

        answerRepository.save(modifyAnswer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        if (answer.voter().contains(siteUser)) {
            return;
        }
        answer.voter().add(siteUser);
        answerRepository.save(answer);
    }
}