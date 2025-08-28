package com.example.GoCafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id", nullable = false, unique = true)
    private Long cafeId;

    @Column(name = "cafe_name", nullable = false, unique = true, length = 10)
    private String cafeName;

    @Column(name = "cafe_address", nullable = false, unique = true, length = 60)
    private String cafeAddress;

    @Column(name = "cafe_lat", nullable = false)
    private Double cafeLat;

    @Column(name = "cafe_lon", nullable = false)
    private Double cafeLon;

    @Column(name = "cafe_number", nullable = false, unique = true, length = 12)
    private String cafeNumber;

    @Column(name = "cafe_date", nullable = false)
    private java.time.LocalDateTime cafeDate;

    @Column(name = "cafe_views")
    private Long cafeViews;

    @Column(name = "cafe_photo", length = 30)
    private String cafePhoto;
}