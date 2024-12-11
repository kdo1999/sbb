package com.sbb.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbb.answer.service.AnswerService;
import com.sbb.controller.request.AnswerForm;
import com.sbb.infrastructure.question.entity.Question;
import com.sbb.infrastructure.siteUser.entity.SiteUser;
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
    public String createAnswer(Model model, @PathVariable("id") Long id, @Valid AnswerForm answer, BindingResult bindingResult, Principal principal) {
        Question question = questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        SiteUser siteUser = siteUserService.findByUsername(principal.getName());

        answerService.create(question, answer.getContent(), siteUser);

        return String.format("redirect:/question/detail/%s", id);
    }
}
