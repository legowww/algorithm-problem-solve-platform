package com.dev.az.service;


import com.dev.az.model.entity.Member;
import com.dev.az.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String join(String name, String email, String password) {
        Member member = Member.of(name, email, bCryptPasswordEncoder.encode(password));
        memberRepository.save(member);

        return member.getId()
                .toString();
    }

    @Transactional
    public String delete(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findByEmail_Email(email);

        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지않는 이메일입니다.");
        }

        Member member = optionalMember.get();

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        memberRepository.delete(member);

        return member.getId()
                .toString();
    }
}
