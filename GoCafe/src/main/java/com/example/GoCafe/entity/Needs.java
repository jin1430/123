package com.example.GoCafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Needs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "needs_id", nullable = false, unique = true)
    private Long needsId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "needs_category_code", length = 20)
    private String needsCategoryCode;

    @Column(name = "needs_code", length = 20)
    private String needsCode;

    @Column(name = "needs_necessary", length = 1)
    private String needsNecessary;
}