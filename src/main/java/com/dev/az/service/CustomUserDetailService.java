package com.dev.az.service;


import com.dev.az.model.entity.Member;
import com.dev.az.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail_Email(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 이메일입니다."));

        return new User(
                member.getId().toString(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority(member.getRole().toString()))
        );
    }
}
