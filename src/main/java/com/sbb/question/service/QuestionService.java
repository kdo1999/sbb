package com.sbb.question.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.answer.entity.AnswerEntity;
import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.infrastructure.question.repository.QuestionRepository;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;

	public List<QuestionEntity> getList() {
		return this.questionRepository.findAll();
	}

	public QuestionEntity getQuestion(Long id) {
		Optional<QuestionEntity> question = questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String subject, String content, SiteUserEntity siteUserEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setSubject(subject);
        questionEntity.setContent(content);
        questionEntity.setCreatedAt(LocalDateTime.now());
		questionEntity.setAuthor(siteUserEntity);

        questionRepository.save(questionEntity);
    }

	public Page<QuestionEntity> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<QuestionEntity> spec = search(kw);
        return questionRepository.findAllByKeyword(kw, pageable);
    }

	public void modify(QuestionEntity questionEntity, String subject, String content) {
        questionEntity.setSubject(subject);
        questionEntity.setContent(content);
        questionEntity.setModifiedAt(LocalDateTime.now());
        this.questionRepository.save(questionEntity);
    }

	public void delete(QuestionEntity questionEntity) {
        questionRepository.delete(questionEntity);
    }

	public void vote(QuestionEntity questionEntity, SiteUserEntity siteUserEntity) {
		if (questionEntity.getVoter().contains(siteUserEntity)) {
			return;
		}
        questionEntity.getVoter().add(siteUserEntity);
        questionRepository.save(questionEntity);
    }

	private Specification<QuestionEntity> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<QuestionEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<QuestionEntity, SiteUserEntity> u1 = q.join("author", JoinType.LEFT);
                Join<QuestionEntity, AnswerEntity> a = q.join("answerList", JoinType.LEFT);
                Join<AnswerEntity, SiteUserEntity> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
}
