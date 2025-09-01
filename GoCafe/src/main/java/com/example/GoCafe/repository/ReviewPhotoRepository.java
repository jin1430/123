package com.example.GoCafe.repository;

import com.example.GoCafe.entity.ReviewPhoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {

    // 화면 표시용: 정렬순(오름차순) + id 보조정렬
    List<ReviewPhoto> findByReviewIdOrderByReviewPhotoSortOrderAscReviewPhotoIdAsc(Long reviewId);

    // 신규 저장 시 시작 정렬값 계산용 (없으면 -1 반환)
    @Query("select coalesce(max(rp.reviewPhotoSortOrder), -1) " +
            "from ReviewPhoto rp where rp.reviewId = :reviewId")
    Integer findMaxSortOrderByReviewId(@Param("reviewId") Long reviewId);

    // 부모 리뷰 삭제 등에서 한 방에 지울 때
    void deleteByReviewId(Long reviewId);
}
