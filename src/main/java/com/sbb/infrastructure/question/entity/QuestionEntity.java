package com.sbb.infrastructure.question.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sbb.infrastructure.answer.entity.AnswerEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;

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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question")
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
	Set<SiteUserEntity> voter;
}
