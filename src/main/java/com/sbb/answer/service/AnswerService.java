package com.sbb.answer.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.answer.entity.AnswerEntity;
import com.sbb.infrastructure.answer.repository.AnswerRepository;
import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;


    public AnswerEntity create(QuestionEntity questionEntity, String content, SiteUserEntity siteUserEntity) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContent(content);
        answerEntity.setCreatedAt(LocalDateTime.now());
        answerEntity.setQuestionEntity(questionEntity);
        answerEntity.setAuthor(siteUserEntity);

		answerRepository.save(answerEntity);

        return answerEntity;
    }

    public AnswerEntity getAnswer(Long id) {
        Optional<AnswerEntity> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(AnswerEntity answerEntity, String content) {
        answerEntity.setContent(content);
        answerEntity.setModifiedAt(LocalDateTime.now());

        answerRepository.save(answerEntity);
    }

    public void delete(AnswerEntity answerEntity) {
        answerRepository.delete(answerEntity);
    }

    public void vote(AnswerEntity answerEntity, SiteUserEntity siteUserEntity) {
        if (answerEntity.getVoter().contains(siteUserEntity)) {
            return;
        }
        answerEntity.getVoter().add(siteUserEntity);
        answerRepository.save(answerEntity);
    }
}