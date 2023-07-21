package com.dev.az.config.jwt;

import com.dev.az.controller.response.Response;
import com.dev.az.util.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String key;

    public JwtAuthorizationFilter(String key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);

            return;
        }

        final String token = header.split(" ")[1].trim();

        try {
            JwtTokenUtils.isExpired(token, key);

            String memberId = JwtTokenUtils.getId(token, key);
            String memberRole = JwtTokenUtils.getMemberRole(token, key);

            User user = new User(memberId, memberId, List.of(new SimpleGrantedAuthority(memberRole)));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");

            new ObjectMapper().writeValue(response.getWriter(), Response.error("세션이 만료되었습니다. 재로그인 하십시오."));
        } catch (JwtException e) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");

            new ObjectMapper().writeValue(response.getWriter(), Response.error("로그인 에러 발생"));
        }
    }
}
