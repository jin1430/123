package com.example.GoCafe.apiController;

import com.example.GoCafe.dto.MemberForm;
import com.example.GoCafe.entity.Member;
import com.example.GoCafe.repository.MemberRepository;
import com.example.GoCafe.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(MemberRepository memberRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberForm body) {
        String email = body.getMemberEmail();
        String pw = body.getMemberPassword();
        if (email == null || email.isBlank() || pw == null || pw.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "email/password required"));
        }

        return memberRepository.findByMemberEmail(email)
                .map(member -> {
                    boolean matches = false;
                    if (member.getMemberPassword() != null) {
                        try {
                            matches = passwordEncoder.matches(pw, member.getMemberPassword());
                        } catch (Exception ignored) {}
                        // 학습용 편의: BCrypt가 아니면 평문도 1회 비교 (운영 제거)
                        if (!matches) matches = pw.equals(member.getMemberPassword());
                    }
                    if (!matches) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("message", "Invalid credentials"));
                    }
                    String token = jwtTokenProvider.generateToken(
                            org.springframework.security.core.userdetails.User
                                    .withUsername(member.getMemberEmail())
                                    .password(member.getMemberPassword())
                                    .authorities(
                                            (member.getMemberRole() != null && member.getMemberRole().startsWith("ROLE_"))
                                                    ? member.getMemberRole()
                                                    : "ROLE_" + (member.getMemberRole() == null ? "USER" : member.getMemberRole())
                                    )
                                    .build()
                    );
                    return ResponseEntity.ok(Map.of("tokenType", "Bearer", "token", token));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid credentials")));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberForm body) {
        String email = body.getMemberEmail();
        String pw = body.getMemberPassword();
        if (email == null || email.isBlank() || pw == null || pw.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "email/password required"));
        }
        if (memberRepository.findByMemberEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email already exists"));
        }

        Member m = new Member();
        m.setMemberEmail(email);
        m.setMemberPassword(passwordEncoder.encode(pw));
        m.setMemberNickname(body.getMemberNickname());
        m.setMemberAge(body.getMemberAge());
        m.setMemberGender(body.getMemberGender());
        m.setMemberRole((body.getMemberRole() == null || body.getMemberRole().isBlank()) ? "USER" : body.getMemberRole());
        m.setMemberDate(body.getMemberDate() == null ? LocalDateTime.now() : body.getMemberDate());
        m.setMemberPhoto(body.getMemberPhoto());

        memberRepository.save(m);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/public/ping")
    public Map<String, String> publicPing() {
        return Map.of("ok", "public");
    }

    @GetMapping("/private/ping")
    public Map<String, String> privatePing() {
        return Map.of("ok", "private");
    }
}
