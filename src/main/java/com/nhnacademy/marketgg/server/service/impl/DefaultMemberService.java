package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.ShopMemberSignupRequest;
import com.nhnacademy.marketgg.server.dto.response.MemberResponse;
import com.nhnacademy.marketgg.server.dto.response.ShopMemberSignupResponse;
import com.nhnacademy.marketgg.server.entity.Member;
import com.nhnacademy.marketgg.server.entity.MemberGrade;
import com.nhnacademy.marketgg.server.exception.member.MemberNotFoundException;
import com.nhnacademy.marketgg.server.exception.membergrade.MemberGradeNotFoundException;
import com.nhnacademy.marketgg.server.repository.member.MemberRepository;
import com.nhnacademy.marketgg.server.repository.membergrade.MemberGradeRepository;
import com.nhnacademy.marketgg.server.service.MemberService;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberGradeRepository memberGradeRepository;

    @Override
    public LocalDateTime retrievePassUpdatedAt(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        if (Objects.isNull(member.getGgpassUpdatedAt())) {
            return LocalDateTime.of(1, 1, 1, 1, 1, 1);
        }
        return member.getGgpassUpdatedAt();
    }

    @Transactional
    @Override
    public void subscribePass(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.passSubscribe();

        // TODO : GG PASS 자동결제 로직 필요

        memberRepository.save(member);
    }

    @Override
    public MemberResponse retrieveMember(String uuid) {
        Member member = memberRepository.findByUuid(uuid)
                                        .orElseThrow(MemberNotFoundException::new);

        return MemberResponse.builder()
                             .memberGrade(member.getMemberGrade())
                             .gender(member.getGender())
                             .birthDay(member.getBirthDate())
                             .ggpassUpdatedAt(member.getGgpassUpdatedAt())
                             .build();
    }

    @Override
    public void withdrawPass(final Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        // TODO : GG PASS 자동결제 해지 로직 필요

        memberRepository.save(member);
    }

    @Transactional
    @Override
    public ShopMemberSignupResponse signup(final ShopMemberSignupRequest shopMemberSignupRequest) {
        if (getReferrerUuid(shopMemberSignupRequest) != null) {

            Member referrerMember =
                memberRepository.findByUuid(getReferrerUuid(shopMemberSignupRequest))
                                .orElseThrow(MemberNotFoundException::new);

            MemberGrade signupMemberGrade = memberGradeRepository.findByGrade("Member")
                                                                 .orElseThrow(
                                                                     MemberGradeNotFoundException::new);

            Member signupMember =
                memberRepository.save(new Member(shopMemberSignupRequest, signupMemberGrade));

            return new ShopMemberSignupResponse(signupMember.getId(), referrerMember.getId());
        }
        MemberGrade signupMemberGrade = memberGradeRepository.findByGrade("Member")
                                                             .orElseThrow(
                                                                 MemberGradeNotFoundException::new);

        Member signupMember =
            memberRepository.save(new Member(shopMemberSignupRequest, signupMemberGrade));

        return new ShopMemberSignupResponse(signupMember.getId(), null);
    }

    private String getReferrerUuid(ShopMemberSignupRequest shopMemberSignupRequest) {
        return shopMemberSignupRequest.getReferrerUuid();
    }

}
