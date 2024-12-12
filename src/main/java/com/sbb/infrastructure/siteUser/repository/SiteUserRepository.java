package com.sbb.infrastructure.siteUser.repository;

import java.util.Optional;

import com.sbb.siteUser.domain.SiteUser;

public interface SiteUserRepository {
	Optional<SiteUser> save(SiteUser siteUser);
	Optional<SiteUser> findByUsername(String username);
}
