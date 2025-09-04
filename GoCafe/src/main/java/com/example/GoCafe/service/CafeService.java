package com.example.GoCafe.service;

import com.example.GoCafe.dto.CafeForm;
import com.example.GoCafe.entity.Cafe;
import com.example.GoCafe.entity.Member;
import com.example.GoCafe.repository.CafeRepository;
import com.example.GoCafe.repository.CafeTagRepository; // 의존성 추가
import com.example.GoCafe.repository.MemberRepository;
import com.example.GoCafe.support.EntityIdUtil;
import com.example.GoCafe.support.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList; // import 추가
import java.util.List;
import java.util.Map;     // import 추가
import java.util.Objects;   // import 추가
import java.util.stream.Collectors; // import 추가

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;
    private final CafeTagRepository cafeTagRepository; // 의존성 주입 추가

    // ... 기존 findAll, findById, create, update, delete, createCafe 메소드들은 그대로 ...

    @Transactional(readOnly = true)
    public List<Cafe> findAll() {
        return cafeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cafe findById(Long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cafe not found: " + id));
    }

    @Transactional
    public Cafe create(Cafe entity) {
        EntityIdUtil.setId(entity, null);
        return cafeRepository.save(entity);
    }

    @Transactional
    public Cafe update(Long id, Cafe entity) {
        if (!cafeRepository.existsById(id)) {
            throw new NotFoundException("Cafe not found: " + id);
        }
        EntityIdUtil.setId(entity, id);
        return cafeRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!cafeRepository.existsById(id)) {
            throw new NotFoundException("Cafe not found: " + id);
        }
        cafeRepository.deleteById(id);
    }

    @Transactional
    public Long createCafe(Long cafeOwnerId, CafeForm form) throws AccessDeniedException {
        Member member = memberRepository.findById(cafeOwnerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (cafeRepository.existsByCafeName(form.getCafeName())) {
            throw new IllegalArgumentException("이미 존재하는 카페명입니다.");
        }
        if (cafeRepository.existsByCafeNumber(form.getCafeNumber())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }
        Cafe saved = cafeRepository.save(form.toEntity());
        if (!member.getMemberRole().equals("owner")) {
            member.setMemberRole("owner");
            memberRepository.save(member);
        }
        return saved.getCafeId();
    }


    // ======================================================
    // ==== 새로 추가되는 추천 기능 메소드 ====
    // ======================================================

    @Transactional(readOnly = true)
    public List<Cafe> findCafesByTags(List<String> tagCodes) {
        if (tagCodes == null || tagCodes.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> sortedCafeIds = cafeTagRepository.findCafeIdsByMostMatchingTags(tagCodes);

        if (sortedCafeIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Cafe> unsortedCafes = cafeRepository.findAllById(sortedCafeIds);

        Map<Long, Cafe> cafeMap = unsortedCafes.stream()
                .collect(Collectors.toMap(Cafe::getCafeId, cafe -> cafe));

        return sortedCafeIds.stream()
                .map(cafeMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}