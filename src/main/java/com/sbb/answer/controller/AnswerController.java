package com.sbb.answer.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.sbb.answer.domain.Answer;
import com.sbb.answer.service.AnswerService;
import com.sbb.controller.request.AnswerForm;
import com.sbb.question.domain.Question;
import com.sbb.question.service.QuestionService;
import com.sbb.siteUser.domain.SiteUser;
import com.sbb.siteUser.service.SiteUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final SiteUserService siteUserService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Long id, @Valid AnswerForm answerForm,
		BindingResult bindingResult, Principal principal) {
		Question question = questionService.getQuestion(id);

		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}

		SiteUser siteUser = siteUserService.findByUsername(principal.getName());

		Answer answer = answerService.create(question,
			answerForm.getContent(), siteUser);

		return String.format("redirect:/question/detail/%s#answer_%s",
			answer.question().id(), answer.id());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Long id, Principal principal) {
		Answer answer = answerService.getAnswer(id);
		if (!answer.author().username().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(answer.content());
		return "answer_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
		@PathVariable("id") Long id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = answerService.getAnswer(id);
		if (!answer.author().username().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerService.modify(answer, answerForm.getContent());

		return String.format("redirect:/question/detail/%s#answer_%s",
			answer.question().id(), answer.id());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Long id) {
		Answer answer = answerService.getAnswer(id);
		if (!answer.author().username().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		answerService.delete(answer);
		return String.format("redirect:/question/detail/%s", answer.question().id());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Long id) {
		Answer answer = answerService.getAnswer(id);
		SiteUser siteUser = siteUserService.findByUsername(principal.getName());
		answerService.vote(answer, siteUser);
		return String.format("redirect:/question/detail/%s#answer_%s",
			answer.question().id(), answer.id());
	}
}
