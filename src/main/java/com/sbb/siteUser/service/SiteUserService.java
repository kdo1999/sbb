package com.sbb.siteUser.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.siteUser.repository.SiteUserRepository;
import com.sbb.siteUser.domain.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SiteUserService {
	private final PasswordEncoder passwordEncoder;
	private final SiteUserRepository siteUserRepository;

	public SiteUser save(String username, String email, String password) {
		Optional<SiteUser> saveSiteUser = siteUserRepository.save(
			SiteUser.builder()
				.username(username)
				.email(email)
				.password(passwordEncoder.encode(password))
				.build()
		);

		if (saveSiteUser.isPresent()) {
			return saveSiteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not saved");
		}
	}

	public SiteUser findByUsername(String username) {
        Optional<SiteUser> siteUser = siteUserRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
