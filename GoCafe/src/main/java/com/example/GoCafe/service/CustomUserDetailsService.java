package com.example.GoCafe.service;

import com.example.GoCafe.entity.Member;
import com.example.GoCafe.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. 이제 MemberRepository에 findByMemberEmail 메소드가 있으므로 정상적으로 회원을 조회합니다.
        Member member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));

        // 2. 조회된 Member 정보를 바탕으로 Spring Security가 사용하는 UserDetails 객체를 생성하여 반환합니다.
        //    이렇게 하면 Member 엔티티를 직접 수정할 필요가 없습니다.
        return User.builder()
                .username(member.getMemberEmail())
                .password(member.getMemberPassword())
                .roles(member.getMemberRole())
                .build();
    }
}