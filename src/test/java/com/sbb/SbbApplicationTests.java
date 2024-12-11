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

import com.sbb.entity.Answer;
import com.sbb.entity.Question;
import com.sbb.repository.AnswerRepository;
import com.sbb.repository.QuestionRepository;
import com.sbb.service.QuestionService;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            questionService.create(subject, content);
        }
    }
}
