package com.example.GoCafe.service;

import com.example.GoCafe.dto.CafeTagForm;
import com.example.GoCafe.entity.CafeTag;
import com.example.GoCafe.repository.CafeTagRepository;
import com.example.GoCafe.support.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeTagService {

    private final CafeTagRepository cafeTagRepository;

    // ======================================================
    // ==== 컨트롤러 오류 해결을 위한 기본 CRUD 메소드 추가 ====
    // ======================================================

    @Transactional(readOnly = true)
    public List<CafeTag> findAll() {
        return cafeTagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CafeTag findById(Long id) {
        return cafeTagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CafeTag not found: " + id));
    }

    // API에서 직접 CafeTag 엔티티를 받아 생성하는 경우
    @Transactional
    public CafeTag create(CafeTag entity) {
        return cafeTagRepository.save(entity);
    }

    // DTO를 사용해 생성하는 경우
    @Transactional
    public Long createCafeTag(CafeTagForm form) {
        CafeTag cafeTag = form.toEntity(); // 기존 방식 그대로 DTO -> Entity 변환
        CafeTag saved = cafeTagRepository.save(cafeTag);
        return saved.getCafeTagId();
    }

    @Transactional
    public CafeTag update(Long id, CafeTag entity) {
        if (!cafeTagRepository.existsById(id)) {
            throw new NotFoundException("CafeTag not found: " + id);
        }
        entity.setCafeTagId(id);
        return cafeTagRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!cafeTagRepository.existsById(id)) {
            throw new NotFoundException("CafeTag not found: " + id);
        }
        cafeTagRepository.deleteById(id);
    }
}