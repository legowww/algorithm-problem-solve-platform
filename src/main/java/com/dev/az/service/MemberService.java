package com.dev.az.service;


import com.dev.az.model.entity.Member;
import com.dev.az.repository.MemberRepository;
import com.dev.az.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createMember(String name, String email) {
        Member member = Member.of(name, email);
        memberRepository.save(member);
    }
}
