package com.example.GoCafe.repository;

import com.example.GoCafe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // AuthViewController, MemberService 등에서 필요한 모든 메소드 선언
    Optional<Member> findByMemberEmail(String memberEmail);
    Optional<Member> findByMemberNickname(String memberNickname);
    boolean existsByMemberEmail(String memberEmail);
    boolean existsByMemberNickname(String memberNickname);

}