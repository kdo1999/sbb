package com.sbb.infrastructure.answer.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.sbb.infrastructure.question.entity.Question;
import com.sbb.infrastructure.siteUser.entity.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

	@ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifiedAt;

    @ManyToMany
    Set<SiteUser> voter;
}