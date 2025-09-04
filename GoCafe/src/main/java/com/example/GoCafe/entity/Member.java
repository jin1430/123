package com.example.GoCafe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Member implements UserDetails { // UserDetails 인터페이스 구현

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, unique = true)
    private Long memberId;

    @Column(name = "member_email", nullable = false, unique = true, length = 100)
    private String memberEmail;

    @Column(name = "member_password", nullable = false, length = 100)
    private String memberPassword;

    @Column(name = "member_nickname", nullable = false, unique = true, length = 8)
    private String memberNickname;

    @Column(name = "member_age")
    private Long memberAge;

    @Column(name = "member_gender", length = 1)
    private String memberGender;

    @Column(name = "member_role", nullable = false, length = 20)
    private String memberRole;

    @Column(name = "member_date", nullable = false)
    private LocalDateTime memberDate;

    @Column(name = "member_photo", length = 30)
    private String memberPhoto;

    @Column(name = "token_version", nullable = false)
    private Long tokenVersion = 0L;

    // ======================================================
    // ==== UserDetails 인터페이스의 필수 메소드 구현 ====
    // ======================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.memberRole));
    }

    @Override
    public String getPassword() {
        return this.memberPassword; // memberPassword 필드를 정확히 사용
    }

    @Override
    public String getUsername() {
        // Security에서 username은 고유 식별자입니다. 여기서는 이메일을 사용합니다.
        return this.memberEmail;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}