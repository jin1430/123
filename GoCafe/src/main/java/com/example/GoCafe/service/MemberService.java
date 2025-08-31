package com.example.GoCafe.service;

import com.example.GoCafe.entity.Member;
import com.example.GoCafe.repository.MemberRepository;
import com.example.GoCafe.support.EntityIdUtil;
import com.example.GoCafe.support.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    @Transactional
    public Member create(Member entity) {
        EntityIdUtil.setId(entity, null);
        return memberRepository.save(entity);
    }

    @Transactional
    public Member update(Long id, Member entity) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        EntityIdUtil.setId(entity, id);
        return memberRepository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
        memberRepository.deleteById(id);
    }

    public Member findByMemberEmail(String memberEmail) {
        return memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new NotFoundException("member not found: " + memberEmail));
    }
}

