package com.example.GoCafe.controller;

import com.example.GoCafe.dto.CafeForm;
import com.example.GoCafe.entity.Cafe;
import com.example.GoCafe.service.CafeService;
import com.example.GoCafe.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/new")
    public String createCafeForm() {
        return "cafes/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCafe(@AuthenticationPrincipal(expression = "username") String memberEmail,
                             @Valid @ModelAttribute("form") CafeForm form,
                             Model model) {

        Long memberId = memberService.findByMemberEmail(memberEmail).getMemberId();

        Long cafeId = cafeService.createCafe(memberId, form); // 내부에서 권한 승격 처리
        return "redirect:/cafes/" + cafeId;
    }

    @GetMapping("/{cafeId}")
    public String viewCafe(@PathVariable Long cafeId, Model model) {
        Cafe cafe = cafeService.findById(cafeId);
        model.addAttribute("cafe", cafe);
        return "cafes/detail";
    }
}