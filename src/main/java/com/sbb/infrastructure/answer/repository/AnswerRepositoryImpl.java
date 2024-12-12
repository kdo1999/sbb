package com.sbb.infrastructure.answer.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sbb.answer.domain.Answer;
import com.sbb.infrastructure.answer.entity.AnswerEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AnswerRepositoryImpl implements AnswerRepository {
	private final AnswerJpaRepository answerJpaRepository;

	@Override
	public Answer save(Answer answer) {
		return answerJpaRepository.save(AnswerEntity.from(answer)).toModel();
	}

	@Override
	public Optional<Answer> findById(Long id) {
		return answerJpaRepository.findById(id).map(AnswerEntity::toModel);
	}

	@Override
	public void delete(Answer answer) {
		answerJpaRepository.delete(AnswerEntity.from(answer));
	}
}