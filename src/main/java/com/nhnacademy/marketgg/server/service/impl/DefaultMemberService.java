package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.service.MemberService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Boolean checkPassUpdatedAt(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return member.getGgpassUpdatedAt().isBefore(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void subscribePass(final Long id) {
            Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
            member.passSubscribe();

            memberRepository.save(member);
    }

    @Override
    public void withdrawPass(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        //TODO : GG PASS 자동결제 해지 로직 필요

        memberRepository.save(member);
    }

}
