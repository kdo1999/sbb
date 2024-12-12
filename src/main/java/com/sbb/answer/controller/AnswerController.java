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

import com.sbb.answer.service.AnswerService;
import com.sbb.controller.request.AnswerForm;
import com.sbb.infrastructure.answer.entity.AnswerEntity;
import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.question.service.QuestionService;
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
		QuestionEntity questionEntity = questionService.getQuestion(id);

		if (bindingResult.hasErrors()) {
			model.addAttribute("question", questionEntity);
			return "question_detail";
		}

		SiteUserEntity siteUserEntity = siteUserService.findByUsername(principal.getName());

		AnswerEntity answerEntity = answerService.create(questionEntity,
			answerForm.getContent(), siteUserEntity);
		return String.format("redirect:/question/detail/%s#answer_%s",
			answerEntity.getQuestionEntity().getId(), answerEntity.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Long id, Principal principal) {
		AnswerEntity answerEntity = answerService.getAnswer(id);
		if (!answerEntity.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerForm.setContent(answerEntity.getContent());
		return "answer_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
		@PathVariable("id") Long id, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		AnswerEntity answerEntity = answerService.getAnswer(id);
		if (!answerEntity.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		answerService.modify(answerEntity, answerForm.getContent());

		return String.format("redirect:/question/detail/%s#answer_%s",
			answerEntity.getQuestionEntity().getId(), answerEntity.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Long id) {
		AnswerEntity answerEntity = answerService.getAnswer(id);
		if (!answerEntity.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		answerService.delete(answerEntity);
		return String.format("redirect:/question/detail/%s", answerEntity.getQuestionEntity().getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Long id) {
		AnswerEntity answerEntity = answerService.getAnswer(id);
		SiteUserEntity siteUserEntity = siteUserService.findByUsername(principal.getName());
		answerService.vote(answerEntity, siteUserEntity);
		return String.format("redirect:/question/detail/%s#answer_%s",
			answerEntity.getQuestionEntity().getId(), answerEntity.getId());
	}
}
