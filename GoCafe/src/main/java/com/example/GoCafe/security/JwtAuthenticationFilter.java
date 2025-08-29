package com.example.GoCafe.security;

import com.example.GoCafe.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   UserDetailsService userDetailsService,
                                   MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        try {
            String auth = req.getHeader("Authorization");
            if (StringUtils.hasText(auth) && auth.toLowerCase().startsWith("bearer ")) {
                String token = auth.substring(7).trim();
                String email = tokenProvider.extractUsername(token);
                Number verClaim = tokenProvider.extractClaim(token, c -> c.get("ver", Number.class)); // null 가능

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var opt = memberRepository.findByMemberEmail(email);
                    if (opt.isPresent()) {
                        long currentVer = opt.get().getTokenVersion() == null ? 0L : opt.get().getTokenVersion();
                        long tokenVer   = verClaim == null ? 0L : verClaim.longValue();

                        if (currentVer != tokenVer) {
                            log.debug("JWT 버전 불일치: tokenVer={}, currentVer={} -> 인증 거부", tokenVer, currentVer);
                            chain.doFilter(req, res);
                            return; // 인증 미설정 → 401 흐름
                        }

                        UserDetails ud = userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        log.debug("JWT 인증 성공: {}", email);
                    }
                }
            }
        } catch (Exception e) {
            log.debug("JWT 처리 실패: {}", e.getMessage());
        }
        chain.doFilter(req, res);
    }
}
