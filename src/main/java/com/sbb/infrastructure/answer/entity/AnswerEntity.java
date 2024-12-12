package com.sbb.infrastructure.answer.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
}