package com.example.GoCafe.dto;

import com.example.GoCafe.entity.Needs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeedsForm {

    private Long needsId;
    private Long userId;
    private String needsCategoryCode;
    private String needsCode;
    private String needsNecessary; // 'Y'/'N'

    public Needs toEntity() {
        return new Needs(
                needsId,
                userId,
                needsCategoryCode,
                needsCode,
                needsNecessary
        );
    }
}
