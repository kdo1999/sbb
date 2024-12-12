package com.sbb.infrastructure.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.infrastructure.answer.entity.AnswerEntity;

public interface AnswerJpaRepository extends JpaRepository<AnswerEntity, Long> {

}
