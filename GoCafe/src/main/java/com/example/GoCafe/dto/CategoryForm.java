package com.example.GoCafe.dto;

import com.example.GoCafe.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForm {

    private Long categoryId;
    private Long cafeId;
    private String category;

    public Category toEntity() {
        return new Category(categoryId, cafeId, category);
    }
}
