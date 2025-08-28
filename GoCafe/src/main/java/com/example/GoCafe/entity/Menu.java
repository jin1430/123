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
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false, unique = true)
    private Long menuId;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "menu_name", nullable = false, length =12)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private Long menuPrice;

    @Column(name = "menu_new", length = 1)
    private String menuNew;

    @Column(name = "menu_recommanded", length = 1)
    private String menuRecommanded;
}