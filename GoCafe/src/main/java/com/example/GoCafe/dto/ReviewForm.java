package com.example.GoCafe.dto;

import com.example.GoCafe.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewForm {

    private Long reviewId;
    private Long cafeId;
    private Long userId;
    private String reviewContent;
    private LocalDateTime reviewDate;
    private Long reviewGood;
    private Long reviewBad;

    public Review toEntity() {
        return new Review(
                reviewId,
                cafeId,
                userId,
                reviewContent,
                reviewDate,
                reviewGood,
                reviewBad
        );
    }
}