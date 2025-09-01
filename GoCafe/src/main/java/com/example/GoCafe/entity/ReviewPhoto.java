package com.example.GoCafe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_photo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ReviewPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_photo_id", nullable = false, unique = true)
    private Long reviewPhotoId;

    // 연관관계 없이 id만 보관하는 현재 설계
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "review_photo_url", nullable = false, length = 1024)
    private String reviewPhotoUrl;

    // ✅ 오름차순 정렬 기준
    @Column(name = "review_photo_sort_order", nullable = false)
    private int reviewPhotoSortOrder; // 원시형이면 미입력=0
}
