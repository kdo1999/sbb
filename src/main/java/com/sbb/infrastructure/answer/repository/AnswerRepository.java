package com.sbb.infrastructure.answer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sbb.answer.domain.Answer;

public interface AnswerRepository {
	Answer save(Answer answer);
	Optional<Answer> findById(Long id);
	void delete(Answer answer);

	Page<Answer> findByQustionId(Long qustionId, Pageable pageable);
	Page<Answer> findByQuestionIdOrderByVoterSize(Long questionId, Pageable pageable);
}
