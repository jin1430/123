package com.example.GoCafe.dto;

import com.example.GoCafe.entity.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagForm {

    private Long reviewTagId;
    private Long reviewId;
    private String tagCategoryCode;
    private String tagCode;

    public ReviewTag toEntity() {
        return new ReviewTag(
                reviewTagId,
                reviewId,
                tagCategoryCode,
                tagCode
        );
    }
}
