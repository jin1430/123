package com.example.GoCafe.controller;

import com.example.GoCafe.entity.Review;
import com.example.GoCafe.entity.ReviewPhoto;
import com.example.GoCafe.service.FileStorageService;
import com.example.GoCafe.service.MemberService;
import com.example.GoCafe.service.ReviewPhotoService;
import com.example.GoCafe.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/reviews")
@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;
    private final FileStorageService fileStorageService;
    private final ReviewPhotoService reviewPhotoService;

    @GetMapping("/new")
    public String createReviewForm() {
        return "/reviews/new";
    }

    @PostMapping(value = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createReview(@AuthenticationPrincipal(expression = "username") String memberEmail,  // ✅ 이메일 주입
                               @RequestParam Long cafeId,
                               @RequestParam String reviewContent,
                               @RequestParam(value = "photos", required = false) List<MultipartFile> photos) {

        // ✅ CafeController와 동일한 방식
        Long userId = memberService.findByMemberEmail(memberEmail).getMemberId();

        Review review = new Review();
        review.setCafeId(cafeId);
        review.setUserId(userId);
        review.setReviewContent(reviewContent);
        review.setReviewDate(LocalDateTime.now());
        review.setReviewGood(0L);
        review.setReviewBad(0L);

        Review saved = reviewService.save(review);

        // 기존 사진 저장 로직 그대로 유지
        int start = reviewPhotoService.findMaxSortOrderByReviewId(saved.getReviewId());
        start = start + 1;

        if (photos != null) {
            int i = 0;
            for (MultipartFile file : photos) {
                if (file.isEmpty()) continue;

                String url = fileStorageService.save(file, "reviews/" + saved.getReviewId());

                ReviewPhoto reviewPhoto = new ReviewPhoto(
                        null,
                        saved.getReviewId(),
                        url,
                        start + i
                );
                reviewPhotoService.save(reviewPhoto);
                i++;
            }
        }

        return "redirect:/cafes/" + cafeId + "#reviews";
    }

}
