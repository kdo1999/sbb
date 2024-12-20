package com.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.infrastructure.siteUser.repository.SiteUserJpaRepository;
import com.sbb.question.service.QuestionService;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private SiteUserJpaRepository siteUserRepository;
	@Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
			SiteUserEntity testUser = siteUserRepository.findByUsername("testUser").get();
			questionService.create(subject, content, testUser);
        }
    }
}
