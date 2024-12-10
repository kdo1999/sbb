package com.sbb;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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
		Question q = this.questionRepository.findBySubjectAndContent(
                "testSubject1", "testContent1");
        assertEquals(1, q.getId());
	}

}
