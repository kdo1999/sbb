package com.sbb.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.infrastructure.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
