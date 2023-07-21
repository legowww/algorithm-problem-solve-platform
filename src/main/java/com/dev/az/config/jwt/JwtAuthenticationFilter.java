package com.dev.az.config.jwt;

import com.dev.az.controller.response.Response;
import com.dev.az.util.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private record LoginRequest(String username, String password) { }

    private final String key;

    private final Long accessTokenExpiredTimeMs;

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(String key, Long accessTokenExpiredTimeMs, AuthenticationManager authenticationManager) {
        this.key = key;
        this.accessTokenExpiredTimeMs = accessTokenExpiredTimeMs;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();

        try {
            LoginRequest loginRequest = om.readValue(request.getInputStream(), LoginRequest.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException("IOException");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        GrantedAuthority role = user.getAuthorities()
                .stream()
                .findFirst()
                .get();

        String authority = role.getAuthority();
        String accessToken = JwtTokenUtils.generateAccessToken(user.getUsername(), authority, key, accessTokenExpiredTimeMs);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(), Response.success(accessToken));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String errorMessage;

        if (failed instanceof UsernameNotFoundException) {
            errorMessage = failed.getMessage();
        }
        else if (failed instanceof BadCredentialsException) {
            errorMessage = "잘못된 비밀번호입니다.";
        }
        else {
            errorMessage = "유효하지 않은 계정 정보입니다.";
        }

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getWriter(), Response.error(errorMessage));
    }
}
