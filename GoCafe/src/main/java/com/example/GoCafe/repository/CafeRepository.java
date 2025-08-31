package com.example.GoCafe.repository;

import com.example.GoCafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    boolean existsByCafeName(String cafeName);
    boolean existsByCafeNumber(String cafeNumber);
}