package com.example.GoCafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Entity
@Table(name = "cafe_tag")
@Getter
@Setter
@AllArgsConstructor
public class CafeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_tag_id", nullable = false, unique = true)
    private Long cafeTagId;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "tag_category_code", length = 20)
    private String tagCategoryCode;

    @Column(name = "tag_code", length = 20)
    private String tagCode;
}
