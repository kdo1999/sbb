package com.sbb.answer.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.sbb.question.domain.Question;
import com.sbb.siteUser.domain.SiteUser;

import lombok.Builder;

public record Answer(
    Long id,
    String content,
    LocalDateTime createdAt,
    Question question,
    SiteUser author,
    LocalDateTime modifiedAt,
	Set<SiteUser> voter
) {

	@Builder
	public Answer(Long id, String content, LocalDateTime createdAt, Question question,
		SiteUser author,
		LocalDateTime modifiedAt, Set<SiteUser> voter) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.question = question;
		this.author = author;
		this.modifiedAt = modifiedAt;
		this.voter = voter == null ? new HashSet<>() : voter;
	}
}
