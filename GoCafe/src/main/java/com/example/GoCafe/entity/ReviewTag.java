package com.example.GoCafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "review_tag")
@Getter
@Setter
@AllArgsConstructor
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id", nullable = false, unique = true)
    private Long reviewTagId;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "tag_category_code", length = 20)
    private String tagCategoryCode;

    @Column(name = "tag_code", length = 20)
    private String tagCode;
}
