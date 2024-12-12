package com.sbb.infrastructure.answer.repository;

import java.util.Optional;

import com.sbb.answer.domain.Answer;

public interface AnswerRepository {
	Answer save(Answer answer);
	Optional<Answer> findById(Long id);
	void delete(Answer answer);
}
