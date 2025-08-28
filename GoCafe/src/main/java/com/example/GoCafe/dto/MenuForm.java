package com.example.GoCafe.dto;

import com.example.GoCafe.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuForm {

    private Long menuId;
    private Long cafeId;
    private Long categoryId;
    private String menuName;
    private int menuPrice;      // 필요 시 BigDecimal로 교체 가능
    private String menuNew;        // 'Y' / 'N'
    private String menuRecommanded; // 'Y' / 'N'

    public Menu toEntity() {
        return new Menu(
                menuId,
                cafeId,
                categoryId,
                menuName,
                menuPrice,
                menuNew,
                menuRecommanded
        );
    }
}
