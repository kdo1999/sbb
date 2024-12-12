package com.sbb.infrastructure.question.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbb.infrastructure.question.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
	QuestionEntity findBySubject(String subject);
	QuestionEntity findBySubjectAndContent(String subject, String content);
	List<QuestionEntity> findBySubjectLike(String subject);
	Page<QuestionEntity> findAll(Pageable pageable);
	@Query("select "
            + "distinct q "
            + "from QuestionEntity q "
            + "left outer join SiteUserEntity u1 on q.author=u1 "
            + "left outer join AnswerEntity a on a.question=q "
            + "left outer join SiteUserEntity u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<QuestionEntity> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
