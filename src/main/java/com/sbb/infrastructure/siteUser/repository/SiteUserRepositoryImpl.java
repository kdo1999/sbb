package com.sbb.infrastructure.siteUser.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.siteUser.domain.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SiteUserRepositoryImpl implements SiteUserRepository{
	private final SiteUserJpaRepository siteUserJpaRepository;
	@Override
	public Optional<SiteUser> save(SiteUser siteUser) {
		return Optional.of(
			siteUserJpaRepository.save(SiteUserEntity.from(siteUser)).toModel()
		);
	}

	@Override
	public Optional<SiteUser> findByUsername(String username) {
		return siteUserJpaRepository.findByUsername(username)
            .map(SiteUserEntity::toModel);
	}
}
