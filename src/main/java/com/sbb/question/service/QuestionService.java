package com.sbb.question.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.question.repository.QuestionRepository;
import com.sbb.question.domain.Question;
import com.sbb.siteUser.domain.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;

	public List<Question> getList() {
		return questionRepository.findAll();
	}

	public Question getQuestion(Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("questionEntity not found");
		}
	}

	public void create(String subject, String content, SiteUser siteUser) {
		Question createQuestion = Question.builder()
			.subject(subject)
			.content(content)
			.createdAt(LocalDateTime.now())
			.author(siteUser).build();

		questionRepository.save(createQuestion);
    }

	public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return questionRepository.findAllByKeyword(kw, pageable);
    }

	public void modify(Question question, String subject, String content) {
		Question modifyQuestion = Question.builder()
			.id(question.id())
			.subject(subject)
			.content(content)
			.author(question.author())
			.voter(question.voter())
			.createdAt(question.createdAt())
			.modifiedAt(LocalDateTime.now())
			.build();

		questionRepository.save(modifyQuestion);
    }

	public void delete(Question question) {
        questionRepository.delete(question);
    }

	public void vote(Question question, SiteUser siteUser) {
		if (question.voter().contains(question)) {
			return;
		}
        question.voter().add(siteUser);
        questionRepository.save(question);
    }
}
