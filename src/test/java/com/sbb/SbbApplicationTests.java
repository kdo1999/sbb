package com.sbb;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbb.entity.Question;
import com.sbb.repository.QuestionRepository;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Transactional
	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("testSubject1");
		q1.setContent("testContent1");
		q1.setCreatedAt(LocalDateTime.now());

		Question q2 = new Question();
		q2.setSubject("testSubject2");
		q2.setContent("testContent2");
		q2.setCreatedAt(LocalDateTime.now());

		questionRepository.save(q1);
		questionRepository.save(q2);
	}

}
