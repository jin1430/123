package com.example.GoCafe.dto;

import com.example.GoCafe.entity.CafeTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CafeTagForm {

    private Long cafeTagId;
    private Long cafeId;
    private String tagCategoryCode;
    private String tagCode;

    public CafeTag toEntity() {
        return new CafeTag(
                cafeTagId,
                cafeId,
                tagCategoryCode,
                tagCode
        );
    }
}
