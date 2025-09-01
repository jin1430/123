package com.example.GoCafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "cafe_info")
@Getter
@Setter
@AllArgsConstructor
public class CafeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_info_id", nullable = false, unique = true)
    private Long cafeInfoId;

    @Column(name = "cafe_id", nullable = false, unique = true)
    private Long cafeId;

    @Column(name = "cafe_notice", length = 20)
    private String cafeNotice;

    @Column(name = "cafe_info", length = 300)
    private String cafeInfo;

    @Column(name = "cafe_open_time", length = 7)
    private String cafeOpenTime;

    @Column(name = "cafe_close_time", length = 7)
    private String cafeCloseTime;

    @Column(name = "cafe_holiday", length = 7)
    private String cafeHoliday;
}
