package com.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
