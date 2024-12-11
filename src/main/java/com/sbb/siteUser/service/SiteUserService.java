package com.sbb.siteUser.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbb.common.exception.DataNotFoundException;
import com.sbb.infrastructure.siteUser.entity.SiteUser;
import com.sbb.infrastructure.siteUser.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SiteUserService {
	private final PasswordEncoder passwordEncoder;
	private final SiteUserRepository siteUserRepository;

	public SiteUser save(String username, String email, String password) {
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(username);
		siteUser.setEmail(email);
		siteUser.setPassword(passwordEncoder.encode(password));

		siteUserRepository.save(siteUser);
		return siteUser;
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
