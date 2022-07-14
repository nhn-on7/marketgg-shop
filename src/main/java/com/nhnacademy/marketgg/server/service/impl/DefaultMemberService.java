package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.repository.MemberRepository;
import com.nhnacademy.marketgg.server.service.MemberService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 서비스를 구현한 구현체입니다.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    /**
     * 회원 Repository 입니다.
     *
     * @since 1.0.0
     */
    private final MemberRepository memberRepository;

    /**
     * 지정한 회원의 GG 패스 갱신일시와 현재일시를 비교하는 메소드입니다.
     *
     * @param id GG 패스 갱신일자를 확인 할 회원의 식별번호입니다.
     * @return 선택한 회원의 GG 패스 갱신일이 현재시간보다 이후 시간이면 TRUE 를 반환, 아니면 False 를 반환합니다.
     * @since 1.0.0
     */
    @Override
    public Boolean checkPassUpdatedAt(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        return member.getGgpassUpdatedAt().isAfter(LocalDateTime.now());
    }

    /**
     * 지정한 회원을 GG 패스에 구독하기 위한 메소드입니다.
     *
     * @param id GG 패스를 구독할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    @Transactional
    @Override
    public void subscribePass(final Long id) {
            Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
            member.passSubscribe();

            //TODO : GG PASS 자동결제 로직 필요

            memberRepository.save(member);
    }

    /**
     * 지정한 회원을 GG 패스에 구독해지하기 위한 메소드입니다.
     *
     * @param id GG 패스를 구독해지할 회원의 식별번호입니다.
     * @since 1.0.0
     */
    @Override
    public void withdrawPass(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        //TODO : GG PASS 자동결제 해지 로직 필요

        memberRepository.save(member);
    }

}
