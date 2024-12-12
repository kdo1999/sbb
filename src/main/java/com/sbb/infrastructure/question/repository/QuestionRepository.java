package com.sbb.infrastructure.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sbb.question.domain.Question;

public interface QuestionRepository {
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	List<Question> findAll();
	Page<Question> findAll(Pageable pageable);
    Page<Question> findAllByKeyword(String kw, Pageable pageable);
	Optional<Question> findById(Long id);
	Question save(Question question);
	void delete(Question question);
}
