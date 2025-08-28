package com.example.GoCafe.dto

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private Long cafeId;
    private String reviewContent;
    private Integer rating;
}