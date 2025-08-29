package com.example.GoCafe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    private String resolveToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization"); // 헤더 이름은 대소문자 무시
        if (StringUtils.hasText(auth)) {
            String lower = auth.toLowerCase();
            if (lower.startsWith("bearer ")) return auth.substring(7).trim();
        }
        // (옵션) 쿼리/쿠키로 전달하는 경우 임시 지원 — 필요 없으면 제거
        String t = request.getParameter("token");
        if (StringUtils.hasText(t)) return t.trim();
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        try {
            String token = resolveToken(req);
            if (StringUtils.hasText(token)) {
                String username = tokenProvider.extractUsername(token); // 여기서 검증 & 만료 체크
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    if (log.isDebugEnabled()) log.debug("JWT 인증 성공: {}", username);
                }
            }
        } catch (Exception e) {
            // 여기서 에러가 나면 인증이 설정되지 않아 401이 떨어집니다. 로그로 이유 확인 가능.
            log.debug("JWT 처리 실패: {}", e.getMessage());
        }
        chain.doFilter(req, res);
    }
}
