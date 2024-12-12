package com.sbb.infrastructure.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.question.domain.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QuestionRepositoryImpl implements QuestionRepository{
	private final QuestionJpaRepository questionJpaRepository;

	@Override
	public Question findBySubject(String subject) {
		QuestionEntity findQuestionEntity = questionJpaRepository.findBySubject(subject);

		return findQuestionEntity.toModel();
	}

	@Override
	public Question findBySubjectAndContent(String subject, String content) {
		return questionJpaRepository.findBySubjectAndContent(subject, content).toModel();
	}

	@Override
	public List<Question> findBySubjectLike(String subject) {
		return questionJpaRepository.findBySubjectLike(subject)
			.stream()
			.map(QuestionEntity::toModel).toList();
	}

	@Override
	public List<Question> findAll() {
		return questionJpaRepository.findAll().stream().map(QuestionEntity::toModel).toList();
	}

	@Override
	public Page<Question> findAll(Pageable pageable) {
		return questionJpaRepository.findAll(pageable).map(QuestionEntity::toModel);
	}

	@Override
	public Page<Question> findAllByKeyword(String kw, Pageable pageable) {
		return questionJpaRepository.findAllByKeyword(kw, pageable).map(QuestionEntity::toModel);
	}

	@Override
	public Optional<Question> findById(Long id) {
		return questionJpaRepository.findById(id).map(QuestionEntity::toModel);
	}

	@Override
	public Question save(Question question) {
		return questionJpaRepository.save(QuestionEntity.from(question)).toModel();
	}

	@Override
	public void delete(Question question) {
		questionJpaRepository.delete(QuestionEntity.from(question));
	}
}
