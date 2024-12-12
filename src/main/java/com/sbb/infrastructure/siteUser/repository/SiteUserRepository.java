package com.sbb.infrastructure.siteUser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;

public interface SiteUserRepository extends JpaRepository<SiteUserEntity, Long> {
	Optional<SiteUserEntity> findByUsername(String username);
}
