package com.example.GoCafe.dto;

import com.example.GoCafe.entity.ReviewPhoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPhotoForm {

    private Long reviewId;             // 부모 리뷰 ID (필수)
    private String reviewPhotoUrl;     // 저장된 파일 경로/URL (필수)
    private int reviewPhotoSortOrder;  // 정렬 순서 (미전송 시 0으로 바인딩됨)

    public ReviewPhoto toEntity() {
        return new ReviewPhoto(
                null,
                reviewId,
                reviewPhotoUrl,
                reviewPhotoSortOrder
        );
    }
}