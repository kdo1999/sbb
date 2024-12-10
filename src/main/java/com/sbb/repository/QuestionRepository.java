package com.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	Question findBySubject(String subject);
}
