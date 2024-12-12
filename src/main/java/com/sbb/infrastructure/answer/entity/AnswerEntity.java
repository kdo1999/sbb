package com.sbb.infrastructure.answer.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.sbb.answer.domain.Answer;
import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.question.domain.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answer")
@NoArgsConstructor
public class AnswerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createdAt;

	@ManyToOne
	private QuestionEntity questionEntity;

	@ManyToOne
	private SiteUserEntity author;

	private LocalDateTime modifiedAt;

	@ManyToMany
	Set<SiteUserEntity> voter;

	@Builder(access = AccessLevel.PROTECTED)
	public AnswerEntity(Long id, String content, LocalDateTime createdAt, QuestionEntity questionEntity,
		SiteUserEntity author, LocalDateTime modifiedAt, Set<SiteUserEntity> voter) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
		this.questionEntity = questionEntity;
		this.author = author;
		this.modifiedAt = modifiedAt;
		this.voter = voter;
	}

	public static AnswerEntity from(Answer answer) {
		return AnswerEntity.builder()
			.id(answer.id())
			.content(answer.content())
			.createdAt(answer.createdAt())
			.questionEntity(QuestionEntity.builder()
				.id(answer.question().id())
				.subject(answer.question().subject())
				.content(answer.question().subject())
				.createdAt(answer.question().createdAt())
				.modifiedAt(answer.question().modifiedAt())
				.author(SiteUserEntity.from(answer.author()))
				.voter(answer.question().voter().isEmpty() ? new HashSet<>() : answer.question().voter().stream().map(siteUser -> SiteUserEntity.from(siteUser)).collect(
					Collectors.toSet()))
				.build()
			)
			.author(SiteUserEntity.from(answer.author()))
			.modifiedAt(answer.modifiedAt())
			.voter(answer.voter().isEmpty() ? new HashSet<>() : answer.voter().stream().map(siteUser -> SiteUserEntity.from(siteUser)).collect(
				Collectors.toSet()))
			.build();
	}

	public Answer toModel() {
		return Answer.builder()
			.id(id)
			.content(content)
			.author(author.toModel())
			.question(Question.builder()
				.id(questionEntity.getId())
					.subject(questionEntity.getSubject())
					.content(questionEntity.getContent())
				.createdAt(questionEntity.getCreatedAt())
				.modifiedAt(questionEntity.getModifiedAt())
				.author(questionEntity.getAuthor().toModel())
				.voter(questionEntity.getVoter().isEmpty() ? new HashSet<>() : questionEntity.getVoter().stream().map(SiteUserEntity::toModel).collect(Collectors.toSet()))
				.build()
			)
			.voter(voter.isEmpty() ? new HashSet<>() : voter.stream().map(SiteUserEntity::toModel).collect(Collectors.toSet()))
			.modifiedAt(modifiedAt)
			.createdAt(createdAt)
			.build();
	}
}