package com.sbb.siteUser.domain;

import lombok.Builder;

public record SiteUser(
	Long id,
	String username,
	String password,
	String email
) {

	@Builder
	public SiteUser(Long id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
