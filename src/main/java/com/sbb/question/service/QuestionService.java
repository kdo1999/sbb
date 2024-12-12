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
import com.sbb.infrastructure.answer.entity.Answer;
import com.sbb.infrastructure.question.entity.Question;
import com.sbb.infrastructure.question.repository.QuestionRepository;
import com.sbb.infrastructure.siteUser.entity.SiteUser;

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

	public List<Question> getList() {
		return this.questionRepository.findAll();
	}

	public Question getQuestion(Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}

	public void create(String subject, String content, SiteUser siteUser) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreatedAt(LocalDateTime.now());
		question.setAuthor(siteUser);

        questionRepository.save(question);
    }

	public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Question> spec = search(kw);
        return questionRepository.findAllByKeyword(kw, pageable);
    }

	public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifiedAt(LocalDateTime.now());
        this.questionRepository.save(question);
    }

	public void delete(Question question) {
        questionRepository.delete(question);
    }

	public void vote(Question question, SiteUser siteUser) {
		if (question.getVoter().contains(siteUser)) {
			return;
		}
        question.getVoter().add(siteUser);
        questionRepository.save(question);
    }

	private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
}
