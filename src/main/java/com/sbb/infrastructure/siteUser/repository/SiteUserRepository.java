package com.sbb.infrastructure.siteUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbb.infrastructure.siteUser.entity.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {

}
