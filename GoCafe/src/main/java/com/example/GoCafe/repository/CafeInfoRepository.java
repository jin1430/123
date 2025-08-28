package com.example.GoCafe.repository;

import com.example.GoCafe.entity.CafeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeInfoRepository extends JpaRepository<CafeInfo, Long> {
}