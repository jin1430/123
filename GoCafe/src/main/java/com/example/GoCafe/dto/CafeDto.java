package com.example.GoCafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeDto {
    private Long cafeId;
    private String cafeName;
    private String cafeAddress;
    private Double cafeLat;
    private Double cafeLon;
    private String cafeNumber;
    private String cafePhoto;
}