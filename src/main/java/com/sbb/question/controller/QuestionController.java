package com.sbb.question.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.sbb.controller.request.AnswerForm;
import com.sbb.infrastructure.question.entity.QuestionEntity;
import com.sbb.infrastructure.siteUser.entity.SiteUserEntity;
import com.sbb.question.controller.request.QuestionForm;
import com.sbb.question.service.QuestionService;
import com.sbb.siteUser.service.SiteUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
	private final QuestionService questionService;
	private final SiteUserService siteUserService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<QuestionEntity> paging = questionService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "question_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id, AnswerForm answerForm) {
		QuestionEntity questionEntity = questionService.getQuestion(id);
		model.addAttribute("question", questionEntity);
		return "question_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}

		SiteUserEntity siteUserEntity = siteUserService.findByUsername(principal.getName());

		questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUserEntity);
		return "redirect:/question/list";
	}

	@PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Long id, Principal principal) {
        QuestionEntity questionEntity =questionService.getQuestion(id);
        if(!questionEntity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(questionEntity.getSubject());
        questionForm.setContent(questionEntity.getContent());
        return "question_form";
    }

	@PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
            Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        QuestionEntity questionEntity = this.questionService.getQuestion(id);
        if (!questionEntity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modify(questionEntity, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

	@PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Long id) {
        QuestionEntity questionEntity = questionService.getQuestion(id);
        if (!questionEntity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.delete(questionEntity);
        return "redirect:/";
    }

	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Long id) {
        QuestionEntity questionEntity = questionService.getQuestion(id);
        SiteUserEntity siteUserEntity = siteUserService.findByUsername(principal.getName());
        questionService.vote(questionEntity, siteUserEntity);
        return String.format("redirect:/question/detail/%s", id);
    }
}