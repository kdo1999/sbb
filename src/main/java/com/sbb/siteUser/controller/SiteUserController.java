package com.sbb.siteUser.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbb.siteUser.controller.request.SiteUserCreateForm;
import com.sbb.siteUser.service.SiteUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class SiteUserController {

	private final SiteUserService siteUserService;

	@GetMapping("/login")
	public String login() {
		return "login_form.html";
	}

	@GetMapping("/signup")
	public String signup(SiteUserCreateForm siteUserCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid SiteUserCreateForm siteUserCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!siteUserCreateForm.getPassword1().equals(siteUserCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect",
				"2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		try {
			siteUserService.save(siteUserCreateForm.getUsername(),
				siteUserCreateForm.getEmail(), siteUserCreateForm.getPassword1());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}

		return "redirect:/";
	}
}
