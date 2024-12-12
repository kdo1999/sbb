package com.sbb.question.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sbb.answer.domain.Answer;
import com.sbb.siteUser.domain.SiteUser;

import lombok.Builder;

public record Question(
	Long id,
	String subject,
	String content,
	LocalDateTime createdAt,
	List<Answer> answerList,
    SiteUser author,
	LocalDateTime modifiedAt,
	Set<SiteUser> voter
) {
	@Builder
	public Question(Long id, String subject, String content, LocalDateTime createdAt,
		List<Answer> answerList,
		SiteUser author, LocalDateTime modifiedAt, Set<SiteUser> voter) {
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.createdAt = createdAt;
		this.answerList = answerList == null ? new ArrayList<>() : answerList;
		this.author = author;
		this.modifiedAt = modifiedAt;
		this.voter = voter;
	}
}
