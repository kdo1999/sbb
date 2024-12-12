package com.sbb.infrastructure.question.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sbb.infrastructure.answer.entity.AnswerEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.question.domain.Question;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question")
@NoArgsConstructor
public class QuestionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE)
	private List<AnswerEntity> answerEntityList = new ArrayList<>();

	@ManyToOne
	private SiteUserEntity author;

	private LocalDateTime modifiedAt;

	@ManyToMany
	Set<SiteUserEntity> voter = new HashSet<>();

	@Builder
	public QuestionEntity(Long id, String subject, String content, LocalDateTime createdAt,
		List<AnswerEntity> answerEntityList, SiteUserEntity author, LocalDateTime modifiedAt,
		Set<SiteUserEntity> voter) {
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.createdAt = createdAt;
		this.answerEntityList = answerEntityList;
		this.author = author;
		this.modifiedAt = modifiedAt;
		this.voter = voter;
	}

	public static QuestionEntity from(Question question) {
		return QuestionEntity.builder()
			.id(question.id())
			.subject(question.subject())
			.content(question.content())
			.createdAt(question.createdAt())
			.answerEntityList(question.answerList().isEmpty() ? new ArrayList<>() :
				question.answerList().stream().map(AnswerEntity::from).toList())
			.author(SiteUserEntity.from(question.author()))
			.modifiedAt(question.modifiedAt())
			.voter(question.voter() != null ? question.voter().stream()
				.map(SiteUserEntity::from)
				.collect(Collectors.toSet()) : new HashSet<>())
			.build();
	}

	public Question toModel() {
		return Question.builder()
			.id(id)
			.subject(subject)
			.content(content)
			.createdAt(createdAt)
			.answerList(answerEntityList.isEmpty() ? new ArrayList<>() : answerEntityList.stream()
				.map(answerEntity -> answerEntity.toModel())
				.toList()
			)
			.author(author.toModel())
			.modifiedAt(modifiedAt)
			.voter(voter.isEmpty() ? new HashSet<>() :
				voter.stream().map(SiteUserEntity::toModel).collect(Collectors.toSet()))
			.build();
	}
}
