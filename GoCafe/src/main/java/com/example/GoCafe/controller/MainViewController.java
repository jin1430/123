package com.example.GoCafe.controller;

import com.example.GoCafe.entity.Cafe;
import com.example.GoCafe.entity.CafeTag;
import com.example.GoCafe.entity.Review;
import com.example.GoCafe.service.CafeService;
import com.example.GoCafe.service.CafeTagService;
import com.example.GoCafe.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainViewController {

    private final CafeService cafeService;
    private final CafeTagService cafeTagService;
    private final ReviewService reviewService;

    @GetMapping({"/", "/main"})
    public String home(Model model) {
        List<Cafe> trending = cafeService.findAll().stream()
                .sorted(Comparator.comparingLong(c -> {
                    Long v = ((Cafe) c).getCafeViews();
                    return v == null ? 0L : v;
                }).reversed())
                .limit(8)
                .collect(Collectors.toList());

        List<CafeTag> tags = cafeTagService.findAll().stream()
                .filter(Objects::nonNull)
                .limit(24)
                .collect(Collectors.toList());

        List<Review> recent = reviewService.findAll().stream()
                .sorted(Comparator.comparing(Review::getReviewDate,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(6)
                .collect(Collectors.toList());

        model.addAttribute("trendingCafes", trending);
        model.addAttribute("cafeTags", tags);
        model.addAttribute("recentReviews", recent);
        return "page/main";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "tag", required = false) String tag,
                         @RequestParam(value = "category", required = false) String category,
                         Model model) {

        List<Cafe> results = cafeService.findAll().stream()
                .filter(c -> {
                    boolean ok = true;
                    if (q != null && !q.isBlank()) {
                        String qq = q.toLowerCase();
                        ok &= (c.getCafeName() != null && c.getCafeName().toLowerCase().contains(qq))
                                || (c.getCafeAddress() != null && c.getCafeAddress().toLowerCase().contains(qq));
                    }
                    if (tag != null && !tag.isBlank()) ok &= true;      // 확장 포인트
                    if (category != null && !category.isBlank()) ok &= true;
                    return ok;
                })
                .limit(40)
                .collect(Collectors.toList());

        List<CafeTag> tags = cafeTagService.findAll().stream().limit(24).collect(Collectors.toList());
        List<Review> recent = reviewService.findAll().stream()
                .sorted(Comparator.comparing(Review::getReviewDate,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(6)
                .collect(Collectors.toList());

        model.addAttribute("trendingCafes", results);
        model.addAttribute("cafeTags", tags);
        model.addAttribute("recentReviews", recent);
        model.addAttribute("query", q);
        model.addAttribute("selectedTag", tag);
        model.addAttribute("selectedCategory", category);
        return "page/main";
    }
}
