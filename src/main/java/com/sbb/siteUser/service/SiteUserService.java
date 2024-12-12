package com.sbb.siteUser.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.infrastructure.siteUser.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SiteUserService {
	private final PasswordEncoder passwordEncoder;
	private final SiteUserRepository siteUserRepository;

	public SiteUserEntity save(String username, String email, String password) {
		SiteUserEntity siteUserEntity = new SiteUserEntity();
		siteUserEntity.setUsername(username);
		siteUserEntity.setEmail(email);
		siteUserEntity.setPassword(passwordEncoder.encode(password));

		siteUserRepository.save(siteUserEntity);
		return siteUserEntity;
	}

	public SiteUserEntity findByUsername(String username) {
        Optional<SiteUserEntity> siteUser = siteUserRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
