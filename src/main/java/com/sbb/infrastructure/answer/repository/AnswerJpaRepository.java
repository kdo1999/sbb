package com.sbb.infrastructure.answer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbb.infrastructure.answer.entity.AnswerEntity;

public interface AnswerJpaRepository extends JpaRepository<AnswerEntity, Long> {

	@Query("select a from AnswerEntity a where a.questionEntity.id = :questionId")
	Page<AnswerEntity> findByQuestionId(@Param("questionId") Long questionId, Pageable pageable);

	@Query("SELECT a FROM AnswerEntity a WHERE a.questionEntity.id = :questionId ORDER BY SIZE(a.voter) DESC")
    Page<AnswerEntity> findByQuestionIdOrderByVoterSize(@Param("questionId") Long questionId, Pageable pageable);

}
