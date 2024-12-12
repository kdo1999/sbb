package com.sbb.infrastructure.siteUser.entity;

import com.sbb.siteUser.domain.SiteUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "site_user")
@NoArgsConstructor
public class SiteUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	@Column(unique = true)
	private String email;

	@Builder
	public SiteUserEntity(Long id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public static SiteUserEntity from(SiteUser siteUser) {
		return SiteUserEntity.builder()
			.id(siteUser.id())
			.email(siteUser.email())
			.username(siteUser.username())
			.password(siteUser.password())
			.build();
	}

	public SiteUser toModel() {
		return SiteUser.builder()
			.id(this.id)
			.email(this.email)
			.username(this.username)
			.password(this.password)
			.build();
	}
}
