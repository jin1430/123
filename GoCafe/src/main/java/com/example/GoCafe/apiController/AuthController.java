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
        m.setMemberPassword(passwordEncoder.encode(pw)); // bcrypt
        m.setMemberNickname(body.getMemberNickname());
        m.setMemberAge(body.getMemberAge());
        m.setMemberGender(body.getMemberGender());
        m.setMemberRole((body.getMemberRole() == null || body.getMemberRole().isBlank()) ? "USER" : body.getMemberRole());
        m.setMemberDate(body.getMemberDate() == null ? LocalDateTime.now() : body.getMemberDate());
        m.setMemberPhoto(body.getMemberPhoto());
        m.setTokenVersion(0L);

        memberRepository.save(m);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
                        // 학습 편의: bcrypt가 아니면 평문 비교 1회 허용 (운영에서는 제거!)
                        if (!matches) matches = pw.equals(member.getMemberPassword());
                    }
                    if (!matches) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("message", "Invalid credentials"));
                    }

                    var ud = org.springframework.security.core.userdetails.User
                            .withUsername(member.getMemberEmail())
                            .password(member.getMemberPassword())
                            .authorities(
                                    (member.getMemberRole()!=null && member.getMemberRole().startsWith("ROLE_"))
                                            ? member.getMemberRole()
                                            : "ROLE_" + (member.getMemberRole()==null ? "USER" : member.getMemberRole())
                            )
                            .build();

                    String token = jwtTokenProvider.generateToken(ud, member.getTokenVersion());
                    return ResponseEntity.ok(Map.of("tokenType","Bearer","token", token));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid credentials")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(org.springframework.security.core.Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = auth.getName();
        return memberRepository.findByMemberEmail(email)
                .map(m -> {
                    long nv = (m.getTokenVersion()==null?0L:m.getTokenVersion()) + 1L;
                    m.setTokenVersion(nv);
                    memberRepository.save(m);
                    return ResponseEntity.noContent().build(); // 204
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(org.springframework.security.core.Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = auth.getName();
        return memberRepository.findByMemberEmail(email)
                .<ResponseEntity<?>>map(m -> {
                    MemberForm out = new MemberForm(
                            m.getMemberId(),
                            m.getMemberEmail(),
                            null, // 비번은 숨김
                            m.getMemberNickname(),
                            m.getMemberAge(),
                            m.getMemberGender(),
                            m.getMemberRole(),
                            m.getMemberDate(),
                            m.getMemberPhoto(),
                            null
                    );
                    return ResponseEntity.ok(out);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found")));
    }

}
