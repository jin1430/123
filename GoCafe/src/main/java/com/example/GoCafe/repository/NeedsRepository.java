package com.example.GoCafe.repository;

import com.example.cafe.entity.Cafe;
import com.example.cafe.entity.Needs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeedsRepository extends JpaRepository<Needs, Long> {
}