package com.example.GoCafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, unique = true)
    private Long reviewId;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "review_content", nullable = false, length = 400)
    private String reviewContent;

    @Column(name = "review_date", nullable = false)
    private java.time.LocalDateTime reviewDate;

    @Column(name = "review_good", nullable = false)
    private Long reviewGood;

    @Column(name = "review_bad", nullable = false)
    private Long reviewBad;
}