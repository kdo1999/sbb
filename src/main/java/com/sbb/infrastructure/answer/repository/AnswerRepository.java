package com.sbb.infrastructure.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.infrastructure.answer.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
