package com.example.GoCafe.dto;

import com.example.GoCafe.entity.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CafeForm {

    private String cafeName;
    private String cafeAddress;
    private Double cafeLat;
    private Double cafeLon;
    private String cafeNumber;
    private Long cafeViews;
    private String cafePhoto; // 선택
    private String cafeCode;

    public Cafe toEntity() {
        return new Cafe(
                null,                   // cafeId (DB auto)
                cafeName,
                cafeAddress,
                cafeLat,
                cafeLon,
                cafeNumber,
                LocalDateTime.now(), // cafeDate (현재시각)
                cafeViews,                    // cafeViews 기본 0
                cafePhoto,
                cafeCode
        );
    }
}
