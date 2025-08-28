package com.example.GoCafe.dto;

import com.example.GoCafe.entity.Cafe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Cafe toEntity() {
        return new Cafe(
                null,                   // cafeId (DB auto)
                cafeName,
                cafeAddress,
                cafeLat,
                cafeLon,
                cafeNumber,
                java.time.LocalDateTime.now(), // cafeDate (현재시각)
                cafeViews,                    // cafeViews 기본 0
                cafePhoto
        );
    }
}
