package com.example.GoCafe.controller;

import com.example.GoCafe.entity.Cafe;
import com.example.GoCafe.service.CafeService;
import com.example.GoCafe.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CafeRecommendationController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private CafeService cafeService;

    @GetMapping("/recommend")
    public String showQuestionnaire(Model model) {
        model.addAttribute("questionnaire", questionnaireService.getQuestionnaire());
        return "recommend/questionnaire";
    }

    @PostMapping("/recommend")
    public String processQuestionnaire(@RequestParam Map<String, String> answers, Model model) {
        List<String> desiredTags = questionnaireService.getTagsFromAnswers(answers);
        List<Cafe> recommendedCafes = cafeService.findCafesByTags(desiredTags);
        model.addAttribute("cafes", recommendedCafes);
        return "recommend/results";
    }
}