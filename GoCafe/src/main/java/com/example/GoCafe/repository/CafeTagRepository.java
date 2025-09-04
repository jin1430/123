package com.example.GoCafe.repository;

import com.example.GoCafe.entity.CafeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CafeTagRepository extends JpaRepository<CafeTag, Long> {

    /**
     * 기존 엔티티 구조를 변경하지 않고 cafeId, tagCode 필드를 직접 사용하는 쿼리
     */
    @Query("SELECT ct.cafeId " +               // ct.cafe.cafeId (X) -> ct.cafeId (O)
            "FROM CafeTag ct " +
            "WHERE ct.tagCode IN :tagCodes " +    // ct.tag.tagCode (X) -> ct.tagCode (O)
            "GROUP BY ct.cafeId " +
            "ORDER BY COUNT(ct.cafeId) DESC, ct.cafeId ASC")
    List<Long> findCafeIdsByMostMatchingTags(@Param("tagCodes") List<String> tagCodes);
}