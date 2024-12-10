package com.sbb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbb.entity.Question;
import com.sbb.exception.DataNotFoundException;
import com.sbb.repository.QuestionRepository;

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

	public void create(String subject, String content) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreatedAt(LocalDateTime.now());

        questionRepository.save(question);
    }
}
