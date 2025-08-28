package com.example.GoCafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false, unique = true)
    private Long menuId;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "menu_name", nullable = false, length = 12)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;

    // 'Y' 또는 'N'
    @Column(name = "menu_new", length = 1)
    private String menuNew;

    // 'Y' 또는 'N'  (DB 컬럼명 철자에 맞춰 매핑)
    @Column(name = "menu_recommanded", length = 1)
    private String menuRecommanded;
}
